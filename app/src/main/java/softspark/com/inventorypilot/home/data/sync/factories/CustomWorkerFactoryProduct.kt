package softspark.com.inventorypilot.home.data.sync.factories

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.sync.product.ProductSyncWorker
import softspark.com.inventorypilot.home.remote.ProductsApi
import javax.inject.Inject

class CustomWorkerFactoryProduct @Inject constructor(
    private val preferences: InventoryPilotPreferences,
    private val productsApi: ProductsApi,
    private val productDao: ProductDao
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            ProductSyncWorker::class.java.name -> ProductSyncWorker(
                appContext,
                workerParameters,
                preferences,
                productsApi,
                productDao
            )

            else -> null
        }
    }
}