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

    companion object {
        private const val VALUE_ZERO = 0
    }

    override suspend fun addProduct(product: Product) {
        productDao.insertProduct(product.toProductEntity())

        resultOf {
            productsApi.insertProduct(product.toAddProductRequest(product.id))
        }.onFailure {
            productDao.insertProductSync(product.toProductSyncEntity())
        }
    }

    override suspend fun getProductsForPage(
        page: Int,
        pageSize: Int
    ): Flow<Result<ArrayList<Product>>> =
        flow<Result<ArrayList<Product>>> {

            val offset = (page - 1) * pageSize

            if (page > VALUE_ZERO) {
                if (networkUtils.isInternetAvailable()) {
                    val apiResult = productsApi.getAllProducts().toProductListDomain()
                    insertProducts(apiResult)
                }
            }

            val localResult = productDao.getProductsForPage(pageSize, offset)
                .map { productEntity -> productEntity.toProductDomain() }

            val valueResult =
                if (localResult.size < pageSize) productDao.getProductsForPage(pageSize, VALUE_ZERO)
                    .map { productEntity -> productEntity.toProductDomain() } else localResult

            emit(Result.Success(data = ArrayList(valueResult)))

        }.onStart {
            emit(Result.Success(data = arrayListOf()))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun getProductsByCategoryId(categoryId: String): Flow<Result<ArrayList<Product>>> =
        flow<Result<ArrayList<Product>>> {
            productDao.getProductsByCategoryId(categoryId).collect {
                val result = it.map { productEntity ->
                    productEntity.toProductDomain()
                }

                emit(Result.Success(ArrayList(result)))
            }

        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun getProductsByName(query: String): Flow<Result<ArrayList<Product>>> =
        flow<Result<ArrayList<Product>>> {
            productDao.getProductsByName(query.lowercase()).collect {
                val result = it.map { productEntity ->
                    productEntity.toProductDomain()
                }

                emit(Result.Success(ArrayList(result)))
            }
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
        val worker = OneTimeWorkRequestBuilder<ProductSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("sync_products_id", ExistingWorkPolicy.REPLACE, worker)
            .enqueue()
    }
}
