package softspark.com.inventorypilot.users.data.sync

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.users.data.local.dao.UserDao
import softspark.com.inventorypilot.users.remote.UserApi
import javax.inject.Inject

class CustomWorkerFactoryUser @Inject constructor(
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val userProfileDao: UserProfileDao
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = UserSyncWorker(
        context = appContext,
        workerParameters = workerParameters,
        userApi = userApi,
        userDao = userDao,
        userProfileDao = userProfileDao
    )
}