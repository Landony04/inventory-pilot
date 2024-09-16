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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants
import softspark.com.inventorypilot.common.utils.Constants.FIVE_MINUTES
import softspark.com.inventorypilot.common.utils.NetworkUtils
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_BRANCH_ID_PREFERENCE
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.mapper.products.toAddCategoryProductRequest
import softspark.com.inventorypilot.home.data.mapper.products.toCategoryDomain
import softspark.com.inventorypilot.home.data.mapper.products.toCategoryEntity
import softspark.com.inventorypilot.home.data.mapper.products.toCategoryListDomain
import softspark.com.inventorypilot.home.data.mapper.products.toCategorySyncEntity
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.data.sync.categoryProduct.CategoryProductSyncWorker
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.remote.ProductsApi
import softspark.com.inventorypilot.home.remote.util.resultOf
import java.time.Duration
import javax.inject.Inject

class ProductCategoriesRepositoryImpl @Inject constructor(
    private val preferences: InventoryPilotPreferences,
    private val dispatchers: DispatcherProvider,
    private val productsApi: ProductsApi,
    private val productCategoryDao: ProductCategoryDao,
    private val networkUtils: NetworkUtils,
    private val workManager: WorkManager
) : ProductCategoriesRepository {
    override suspend fun addCategoryProduct(productCategory: ProductCategory) {
        productCategoryDao.insertCategory(productCategory.toCategoryEntity())

        delay(Constants.DELAY_TIME)
        resultOf {
            productsApi.insertCategory(
                preferences.getValuesString(USER_BRANCH_ID_PREFERENCE),
                productCategory.toAddCategoryProductRequest(productCategory.id)
            )
        }.onFailure {
            productCategoryDao.insertCategoryProductSync(productCategory.toCategorySyncEntity())
        }
    }

    override suspend fun getAllCategories(): Flow<Result<ArrayList<ProductCategory>>> =
        flow<Result<ArrayList<ProductCategory>>> {

            if (networkUtils.isInternetAvailable()) {
                val apiResult = productsApi.getProductCategories(
                    preferences.getValuesString(USER_BRANCH_ID_PREFERENCE)
                ).toCategoryListDomain()

                insertProductCategories(apiResult)
            }

            val localResult =
                productCategoryDao.getProductCategories()
                    .map { category -> category.toCategoryDomain() }.sortedBy { it.name }

            emit(Result.Success(data = ArrayList(localResult)))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun getCategoryById(id: String): Flow<Result<ProductCategory>> =
        flow<Result<ProductCategory>> {
            emit(Result.Success(ProductCategory("", "")))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun insertProductCategory(productCategory: ProductCategory): Flow<Result<Boolean>> =
        flow<Result<Boolean>> {
            emit(Result.Success(true))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun insertProductCategories(productCategories: List<ProductCategory>) =
        withContext(dispatchers.io()) {
            productCategoryDao.insertCategories(productCategories.map { productCategory -> async { productCategory.toCategoryEntity() }.await() })
        }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun syncCategoryProduct() {
        val worker = OneTimeWorkRequestBuilder<CategoryProductSyncWorker>()
            .setConstraints(
                Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
            )
            .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(FIVE_MINUTES))
            .build()

        workManager
            .beginUniqueWork(
                "sync_categories_products_id",
                ExistingWorkPolicy.REPLACE,
                worker
            )
            .enqueue()
    }
}
