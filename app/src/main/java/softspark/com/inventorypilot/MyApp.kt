package softspark.com.inventorypilot

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import dagger.hilt.android.HiltAndroidApp
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.sync.SalesSyncWorker
import softspark.com.inventorypilot.home.data.sync.product.ProductSyncWorker
import softspark.com.inventorypilot.home.remote.ProductsApi
import softspark.com.inventorypilot.home.remote.SalesApi
import softspark.com.inventorypilot.users.data.sync.CustomWorkerFactoryUser
import javax.inject.Inject

@HiltAndroidApp
class MyApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: CustomWorkerFactory

    @Inject
    lateinit var workerFactoryProduct: CustomWorkerFactoryProduct

    @Inject
    lateinit var workerFactoryUser: CustomWorkerFactoryUser

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder()
            .setMinimumLoggingLevel(Log.DEBUG)
            .setWorkerFactory(workerFactory)
            .setWorkerFactory(workerFactoryProduct)
            .setWorkerFactory(workerFactoryUser)
            .build()
    }
}

class CustomWorkerFactory @Inject constructor(
    private val salesApi: SalesApi,
    private val salesDao: SalesDao,
    private val productDao: ProductDao
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = SalesSyncWorker(
        context = appContext,
        workerParameters = workerParameters,
        salesApi = salesApi,
        salesDao = salesDao,
        productDao = productDao
    )
}

class CustomWorkerFactoryProduct @Inject constructor(
    private val productsApi: ProductsApi,
    private val productDao: ProductDao
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = ProductSyncWorker(
        context = appContext,
        workerParameters = workerParameters,
        productsApi = productsApi,
        productDao = productDao
    )
}
