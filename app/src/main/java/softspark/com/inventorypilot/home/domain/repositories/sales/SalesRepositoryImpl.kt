package softspark.com.inventorypilot.home.domain.repositories.sales

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ZERO
import softspark.com.inventorypilot.common.utils.NetworkUtils
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleDomain
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleEntity
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleListDomain
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.remote.SalesApi
import javax.inject.Inject

class SalesRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val networkUtils: NetworkUtils,
    private val salesApi: SalesApi,
    private val salesDao: SalesDao
) : SalesRepository {
    override suspend fun getSalesForPage(
        page: Int,
        pageSize: Int
    ): Flow<Result<ArrayList<Sale>>> =
        flow<Result<ArrayList<Sale>>> {

            val offset = (page - 1) * pageSize

            if (page > VALUE_ZERO) {
                if (networkUtils.isInternetAvailable()) {
                    val apiResult = salesApi.getAllSales().toSaleListDomain()

                    insertSales(apiResult)
                }
            }

            val localResult = salesDao.getSalesForPage(pageSize, offset)
                .map { saleEntity -> saleEntity.toSaleDomain() }

            val valueResult =
                if (localResult.size < pageSize) salesDao.getSalesForPage(pageSize, VALUE_ZERO)
                    .map { saleEntity -> saleEntity.toSaleDomain() } else localResult

            emit(Result.Success(data = ArrayList(valueResult)))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun getSalesByDate(date: String): Flow<Result<ArrayList<Sale>>> =
        flow<Result<ArrayList<Sale>>> {

            val localResult =
                salesDao.getSalesByDate(date).map { saleEntity -> saleEntity.toSaleDomain() }

            emit(Result.Success(data = ArrayList(localResult)))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun insertSales(sales: List<Sale>) = withContext(dispatchers.io()) {
        salesDao.insertSales(sales.map { sale -> async { sale.toSaleEntity() }.await() })
    }
}
