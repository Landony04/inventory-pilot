package softspark.com.inventorypilot.home.data.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import softspark.com.inventorypilot.home.data.sync.factories.CustomWorkerFactory
import softspark.com.inventorypilot.home.data.sync.factories.CustomWorkerFactoryCategoryProduct
import softspark.com.inventorypilot.home.data.sync.factories.CustomWorkerFactoryProduct
import softspark.com.inventorypilot.users.data.sync.CustomWorkerFactoryUser
import javax.inject.Inject

class CombinedWorkerFactory @Inject constructor(
    private val customWorkerFactory: CustomWorkerFactory,
    private val customWorkerFactoryProduct: CustomWorkerFactoryProduct,
    private val customWorkerFactoryUser: CustomWorkerFactoryUser,
    private val customWorkerFactoryCategoryProduct: CustomWorkerFactoryCategoryProduct
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {

        return customWorkerFactory.createWorker(appContext, workerClassName, workerParameters)
            ?: customWorkerFactoryProduct.createWorker(
                appContext,
                workerClassName,
                workerParameters
            )
            ?: customWorkerFactoryUser.createWorker(appContext, workerClassName, workerParameters)
            ?: customWorkerFactoryCategoryProduct.createWorker(
                appContext,
                workerClassName,
                workerParameters
            )
    }
}