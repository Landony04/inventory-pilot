package softspark.com.inventorypilot.home.domain.repositories.sales

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
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
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleDomain
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleEntity
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleListDomain
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleRequestDto
import softspark.com.inventorypilot.home.data.mapper.sales.toSyncEntity
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import softspark.com.inventorypilot.home.data.sync.SalesSyncWorker
import softspark.com.inventorypilot.home.domain.models.sales.ProductSale
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.remote.SalesApi
import softspark.com.inventorypilot.home.remote.dto.products.UpdateProductRequest
import softspark.com.inventorypilot.home.remote.util.resultOf
import java.time.Duration
import javax.inject.Inject

class SalesRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val networkUtils: NetworkUtils,
    private val productDao: ProductDao,
    private val salesApi: SalesApi,
    private val salesDao: SalesDao,
    private val workManager: WorkManager
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

    override suspend fun insertSale(sale: Sale) {
        salesDao.insertSale(sale.toSaleEntity())

        resultOf {
            salesApi.insertSale(sale.toSaleRequestDto())
            updateProductsStock(sale.products.toList())
        }.onFailure {
            salesDao.insertSaleSync(sale.toSyncEntity())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun syncSales() {
        val worker = OneTimeWorkRequestBuilder<SalesSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("sync_sales_id", ExistingWorkPolicy.REPLACE, worker).enqueue()
    }

    override suspend fun updateProductsStock(products: List<ProductSale>) =
        withContext(dispatchers.io()) {
            for (soldProduct in products) {
                val product = getCurrentStockFromDataBase(soldProduct.id)
                product?.let { productValue ->
                    val newStock = productValue.stock - soldProduct.quantity
                    if (newStock >= VALUE_ZERO) {
                        salesApi.updateProductStock(
                            soldProduct.id,
                            UpdateProductRequest(
                                stock = newStock
                            )
                        )
                    } else {
                        println("Stock insuficiente para el producto ${soldProduct.id}")
                    }
                }
            }
        }

    override suspend fun getCurrentStockFromDataBase(productId: String): ProductEntity? {
        return try {
            productDao.getProductById(productId)
        } catch (exception: Exception) {
            println("Error: ${exception.message}")
            null
        }
    }
}
