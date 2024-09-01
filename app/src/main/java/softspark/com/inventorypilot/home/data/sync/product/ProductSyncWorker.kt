package softspark.com.inventorypilot.home.data.sync.product

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.entity.products.ProductSyncEntity
import softspark.com.inventorypilot.home.data.mapper.products.toAddProductRequest
import softspark.com.inventorypilot.home.data.mapper.products.toProductDomain
import softspark.com.inventorypilot.home.data.mapper.products.toProductSyncEntity
import softspark.com.inventorypilot.home.remote.ProductsApi
import softspark.com.inventorypilot.home.remote.util.resultOf

@HiltWorker
class ProductSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val productsApi: ProductsApi,
    private val productDao: ProductDao
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val productToSync = productDao.getAllProductSync()

        if (runAttemptCount >= 3) {
            return Result.failure()
        }

        return try {
            supervisorScope {
                val job = productToSync.map { sale -> async { sync(sale) } }
                job.awaitAll()
            }
            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }

    private suspend fun sync(productSyncEntity: ProductSyncEntity) {
        val product =
            productDao.getProductById(productSyncEntity.id)
        val productDomain = product.toProductDomain()
        resultOf {
            productsApi.insertProduct(productDomain.toAddProductRequest(productDomain.id))
        }.onSuccess {
            productDao.deleteProductSync(product.toProductSyncEntity())
        }.onFailure {
            throw it
        }
    }
}