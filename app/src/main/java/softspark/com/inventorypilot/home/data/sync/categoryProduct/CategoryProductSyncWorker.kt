package softspark.com.inventorypilot.home.data.sync.categoryProduct

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_BRANCH_ID_PREFERENCE
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.local.entity.products.CategoryProductSyncEntity
import softspark.com.inventorypilot.home.data.mapper.products.toAddCategoryProductRequest
import softspark.com.inventorypilot.home.data.mapper.products.toCategoryDomain
import softspark.com.inventorypilot.home.remote.ProductsApi
import softspark.com.inventorypilot.home.remote.util.resultOf

@HiltWorker
class CategoryProductSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val preferences: InventoryPilotPreferences,
    private val productsApi: ProductsApi,
    private val productCategoryDao: ProductCategoryDao
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val categoryProductToSync = productCategoryDao.getAllCategoriesProductSync()

        if (runAttemptCount >= 3) {
            return Result.failure()
        }

        return try {
            supervisorScope {
                val job = categoryProductToSync.map { category -> async { sync(category) } }
                job.awaitAll()
            }
            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }

    private suspend fun sync(categoryProductSyncEntity: CategoryProductSyncEntity) {
        val categoryProduct = productCategoryDao.getCategoryById(categoryProductSyncEntity.id)

        resultOf {
            productsApi.insertCategory(
                preferences.getValuesString(USER_BRANCH_ID_PREFERENCE),
                categoryProduct.toCategoryDomain()
                    .toAddCategoryProductRequest(categoryProduct.categoryId)
            )
        }.onSuccess {
            productCategoryDao.deleteCategoryProductSync(categoryProductSyncEntity)
        }.onFailure {
            throw it
        }
    }
}