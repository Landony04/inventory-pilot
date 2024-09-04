package softspark.com.inventorypilot.users.data.sync

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.supervisorScope
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.home.remote.util.resultOf
import softspark.com.inventorypilot.login.data.mapper.toUserProfile
import softspark.com.inventorypilot.users.data.local.dao.UserDao
import softspark.com.inventorypilot.users.data.local.entity.user.UserSyncEntity
import softspark.com.inventorypilot.users.data.mapper.toAddUserRequest
import softspark.com.inventorypilot.users.remote.UserApi

@HiltWorker
class UserSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParameters: WorkerParameters,
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val userProfileDao: UserProfileDao
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        val usersToSync = userDao.getAllUserSync()

        if (runAttemptCount >= 3) {
            return Result.failure()
        }

        return try {
            supervisorScope {
                val job = usersToSync.map { user -> async { sync(user) } }
                job.awaitAll()
            }
            Result.success()
        } catch (exception: Exception) {
            Result.retry()
        }
    }

    private suspend fun sync(userSyncEntity: UserSyncEntity) {
        val user =
            userProfileDao.getUserProfileById(userSyncEntity.id).toUserProfile()
        resultOf {
            userApi.addUser(user.toAddUserRequest(userSyncEntity.id))
        }.onSuccess {
            userDao.deleteUserSync(userSyncEntity)
        }.onFailure {
            throw it
        }
    }
}