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
import softspark.com.inventorypilot.common.utils.Constants.RETRY_SEND_DATA_FROM_WORK
import softspark.com.inventorypilot.home.remote.util.resultOf
import softspark.com.inventorypilot.login.data.mapper.toUserProfile
import softspark.com.inventorypilot.users.data.local.dao.UserDao
import softspark.com.inventorypilot.users.data.local.entity.user.UpdateUserSyncEntity
import softspark.com.inventorypilot.users.data.local.entity.user.UserSyncEntity
import softspark.com.inventorypilot.users.data.mapper.toAddUserRequest
import softspark.com.inventorypilot.users.remote.UserApi
import softspark.com.inventorypilot.users.remote.dto.user.ModifiedUserRequest

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
        val usersUpdateToSync = userDao.getAllUserUpdateSync()

        if (runAttemptCount >= RETRY_SEND_DATA_FROM_WORK) {
            return Result.failure()
        }

        return try {
            supervisorScope {
                if (usersToSync.isNotEmpty()) {
                    val job = usersToSync.map { user -> async { sync(user) } }
                    job.awaitAll()
                }

                if (usersUpdateToSync.isNotEmpty()) {
                    val jobUpdate = usersUpdateToSync.map { user -> async { syncUserUpdate(user) } }
                    jobUpdate.awaitAll()
                }
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

    private suspend fun syncUserUpdate(updateUserSyncEntity: UpdateUserSyncEntity) {
        val user =
            userProfileDao.getUserProfileById(updateUserSyncEntity.id).toUserProfile()
        resultOf {
            userApi.changeUserStatus(userId = user.id, ModifiedUserRequest(status = user.status))
        }.onSuccess {
            userDao.deleteUserUpdateSync(updateUserSyncEntity)
        }.onFailure {
            throw it
        }
    }
}