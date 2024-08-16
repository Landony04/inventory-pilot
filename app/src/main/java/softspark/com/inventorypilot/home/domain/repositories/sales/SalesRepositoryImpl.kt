package softspark.com.inventorypilot.home.domain.repositories.sales

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
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
    private val salesApi: SalesApi,
    private val salesDao: SalesDao
) : SalesRepository {
    override suspend fun getAllSales(): Flow<Result<ArrayList<Sale>>> =
        flow<Result<ArrayList<Sale>>> {

            val apiResult = salesApi.getAllSales().toSaleListDomain()

            insertSales(apiResult)

            val localResult = salesDao.getAllSales().map { saleEntity -> saleEntity.toSaleDomain() }

            emit(Result.Success(data = ArrayList(localResult)))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun insertSales(sales: List<Sale>) = withContext(dispatchers.io()) {
        salesDao.insertSales(sales.map { sale -> async { sale.toSaleEntity() }.await() })
    }
}
