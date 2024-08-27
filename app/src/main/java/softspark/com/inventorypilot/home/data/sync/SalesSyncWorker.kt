package softspark.com.inventorypilot.home.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import kotlinx.coroutines.supervisorScope
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleSyncEntity
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleDomain
import softspark.com.inventorypilot.home.data.mapper.sales.toSaleRequestDto
import softspark.com.inventorypilot.home.remote.SalesApi
import softspark.com.inventorypilot.home.remote.util.resultOf

@HiltWorker
class SalesSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val salesApi: SalesApi,
    private val salesDao: SalesDao
) : CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result {
        val salesToSync = salesDao.getAllSalesSync()

        if (runAttemptCount >= 3) {
            return Result.failure()
        }

        return try {
            supervisorScope {
                val job = salesToSync.map { sale -> launch { sync(sale) } }
                job.forEach { it.join() }
            }
            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }

    private suspend fun sync(saleSyncEntity: SaleSyncEntity) {
        val sale =
            salesDao.getSaleById(saleSyncEntity.id).toSaleDomain().toSaleRequestDto()
        resultOf {
            salesApi.insertSale(sale)
        }.onSuccess {
            salesDao.deleteSaleSync(saleSyncEntity)
        }.onFailure {
            throw it
        }
    }
}