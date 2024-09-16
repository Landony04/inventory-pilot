package softspark.com.inventorypilot.home.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ZERO
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_BRANCH_ID_PREFERENCE
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleSyncEntity
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleDomain
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleRequestDto
import softspark.com.inventorypilot.home.domain.models.sales.ProductSale
import softspark.com.inventorypilot.home.remote.SalesApi
import softspark.com.inventorypilot.home.remote.dto.products.UpdateProductRequest
import softspark.com.inventorypilot.home.remote.util.resultOf

@HiltWorker
class SalesSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val preferences: InventoryPilotPreferences,
    private val salesApi: SalesApi,
    private val salesDao: SalesDao,
    private val productDao: ProductDao
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val salesToSync = salesDao.getAllSalesSync()

        if (runAttemptCount >= 3) {
            return Result.failure()
        }

        return try {
            supervisorScope {
                val job = salesToSync.map { sale -> async { sync(sale) } }
                job.awaitAll()
            }
            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }

    private suspend fun sync(saleSyncEntity: SaleSyncEntity) {
        val sale =
            salesDao.getSaleById(saleSyncEntity.id).toSaleDomain().toSaleRequestDto()
        val products = salesDao.getSaleById(saleSyncEntity.id).products
        resultOf {
            salesApi.insertSale(
                preferences.getValuesString(USER_BRANCH_ID_PREFERENCE),
                sale
            )
            updateProductsStock(products.products)
        }.onSuccess {
            salesDao.deleteSaleSync(saleSyncEntity)
        }.onFailure {
            throw it
        }
    }

    private suspend fun updateProductsStock(products: List<ProductSale>) {
        for (soldProduct in products) {
            val product = getCurrentStockFromDataBase(soldProduct.id)
            product?.let { productValue ->
                val newStock = productValue.stock - soldProduct.quantity
                if (newStock >= VALUE_ZERO) {
                    salesApi.updateProductStock(
                        preferences.getValuesString(USER_BRANCH_ID_PREFERENCE),
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

    private fun getCurrentStockFromDataBase(productId: String): ProductEntity? {
        return try {
            productDao.getProductById(productId)
        } catch (exception: Exception) {
            println("Error: ${exception.message}")
            null
        }
    }
}