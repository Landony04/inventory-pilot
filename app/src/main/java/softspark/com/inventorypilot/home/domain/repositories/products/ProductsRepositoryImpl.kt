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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.FIVE_MINUTES
import softspark.com.inventorypilot.common.utils.NetworkUtils
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_BRANCH_ID_PREFERENCE
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
    private val preferences: InventoryPilotPreferences,
    private val dispatchers: DispatcherProvider,
    private val productsApi: ProductsApi,
    private val productDao: ProductDao,
    private val networkUtils: NetworkUtils,
    private val workManager: WorkManager
) : ProductsRepository {

    override suspend fun addProduct(product: Product) {
        productDao.insertProduct(product.toProductEntity())

        resultOf {
            productsApi.insertOrUpdateProduct(
                preferences.getValuesString(USER_BRANCH_ID_PREFERENCE),
                product.toAddProductRequest(product.id)
            )
        }.onFailure {
            productDao.insertProductSync(product.toProductSyncEntity())
        }
    }

    override suspend fun getProductsForPage(): Flow<List<Product>> = flow {
        if (networkUtils.isInternetAvailable()) {
            try {
                val apiResult =
                    productsApi.getAllProducts(preferences.getValuesString(USER_BRANCH_ID_PREFERENCE))
                        .toProductListDomain()
                insertProducts(apiResult)
            } catch (exception: Exception) {
                println("exception: ${exception.message}")
            }
        }

        productDao.getProductsForPage().collect {
            emit(it.map { productEntity -> productEntity.toProductDomain() })
        }
    }.flowOn(dispatchers.io())

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

    override suspend fun getProductsById(productId: String): Flow<Result<Product>> =
        flow<Result<Product>> {

            val product = productDao.getProductById(productId).toProductDomain()

            emit(Result.Success(data = product))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun insertProducts(products: List<Product>) = withContext(dispatchers.io()) {
        productDao.insertProducts(products.map { product -> async { product.toProductEntity() }.await() })
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun syncProducts() {
        val worker = OneTimeWorkRequestBuilder<ProductSyncWorker>()
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(FIVE_MINUTES))
            .build()

        workManager
            .beginUniqueWork("sync_products_id", ExistingWorkPolicy.REPLACE, worker)
            .enqueue()
    }

    override suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product.toProductEntity())

        resultOf {
            productsApi.insertOrUpdateProduct(
                preferences.getValuesString(USER_BRANCH_ID_PREFERENCE),
                product.toAddProductRequest(product.id)
            )
        }.onFailure {
            productDao.insertProductSync(product.toProductSyncEntity())
        }
    }
}
