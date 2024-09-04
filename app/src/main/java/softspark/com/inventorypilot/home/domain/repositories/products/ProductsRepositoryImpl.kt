package softspark.com.inventorypilot.home.domain.repositories.products

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.utils.NetworkUtils
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.mapper.products.toAddProductRequest
import softspark.com.inventorypilot.home.data.mapper.products.toProductDomain
import softspark.com.inventorypilot.home.data.mapper.products.toProductEntity
import softspark.com.inventorypilot.home.data.mapper.products.toProductListDomain
import softspark.com.inventorypilot.home.data.mapper.products.toProductSyncEntity
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.data.sync.product.ProductSyncWorker
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.remote.ProductsApi
import softspark.com.inventorypilot.home.remote.util.resultOf
import java.time.Duration
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val productsApi: ProductsApi,
    private val productDao: ProductDao,
    private val networkUtils: NetworkUtils,
    private val workManager: WorkManager
) : ProductsRepository {

    override suspend fun addProduct(product: Product) {
        productDao.insertProduct(product.toProductEntity())

        resultOf {
            productsApi.insertProduct(product.toAddProductRequest(product.id))
        }.onFailure {
            productDao.insertProductSync(product.toProductSyncEntity())
        }
    }

    override suspend fun getProductsForPage(
        limit: Int,
        offset: Int
    ): List<Product> = withContext(dispatchers.io()) {

        if (networkUtils.isInternetAvailable()) {
            val apiResult = productsApi.getAllProducts().toProductListDomain()
            insertProducts(apiResult)
        }

        return@withContext productDao.getProductsForPage(limit, offset)
            .map { productEntity -> productEntity.toProductDomain() }
    }

    override suspend fun getProductsByCategoryId(categoryId: String): List<Product> =
        withContext(dispatchers.io()) {
            return@withContext productDao.getProductsByCategoryId(categoryId)
                .map { productEntity -> productEntity.toProductDomain() }
        }

    override suspend fun getProductsByName(query: String): List<Product> =
        withContext(dispatchers.io()) {
            return@withContext productDao.getProductsByName(query.lowercase())
                .map { productEntity -> productEntity.toProductDomain() }
        }

    override suspend fun insertProducts(products: List<Product>) = withContext(dispatchers.io()) {
        productDao.insertProducts(products.map { product -> async { product.toProductEntity() }.await() })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun syncProducts() {
        val worker = OneTimeWorkRequestBuilder<ProductSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("sync_products_id", ExistingWorkPolicy.REPLACE, worker)
            .enqueue()
    }
}
