package softspark.com.inventorypilot.home.data.sync.factories

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.sync.SalesSyncWorker
import softspark.com.inventorypilot.home.remote.SalesApi
import javax.inject.Inject

class CustomWorkerFactory @Inject constructor(
    private val preferences: InventoryPilotPreferences,
    private val salesApi: SalesApi,
    private val salesDao: SalesDao,
    private val productDao: ProductDao
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        return when (workerClassName) {
            SalesSyncWorker::class.java.name -> SalesSyncWorker(
                appContext,
                workerParameters,
                preferences,
                salesApi,
                salesDao,
                productDao
            )

            else -> null
        }
    }
}