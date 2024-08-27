package softspark.com.inventorypilot.home.data.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import javax.inject.Inject
import javax.inject.Provider

class CustomWorkerFactory @Inject constructor(
    private val workerProviders: Map<Class<out ListenableWorker>, @JvmSuppressWildcards Provider<ChildWorkerFactory>>
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker? {
        val foundEntry =
            workerProviders.entries.find { Class.forName(workerClassName).isAssignableFrom(it.key) }
        val factoryProvider = foundEntry?.value
        return factoryProvider?.get()?.create(appContext, workerParameters)
    }
}

interface ChildWorkerFactory {
    fun create(appContext: Context, workerParameters: WorkerParameters): ListenableWorker
}