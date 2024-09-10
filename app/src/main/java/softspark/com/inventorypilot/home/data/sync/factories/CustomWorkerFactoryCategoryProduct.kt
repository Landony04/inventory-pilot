package softspark.com.inventorypilot.home.data.sync.factories

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.sync.categoryProduct.CategoryProductSyncWorker
import softspark.com.inventorypilot.home.remote.ProductsApi
import javax.inject.Inject

class CustomWorkerFactoryCategoryProduct @Inject constructor(
    private val productsApi: ProductsApi,
    private val productCategoryDao: ProductCategoryDao
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = CategoryProductSyncWorker(
        context = appContext,
        workerParameters = workerParameters,
        productsApi = productsApi,
        productCategoryDao = productCategoryDao
    )
}