package softspark.com.inventorypilot.users.domain.repositories

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.utils.NetworkUtils
import softspark.com.inventorypilot.home.data.sync.product.ProductSyncWorker
import softspark.com.inventorypilot.home.remote.util.resultOf
import softspark.com.inventorypilot.login.data.mapper.toEntity
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.users.data.local.dao.UserDao
import softspark.com.inventorypilot.users.data.mapper.toAddUserRequest
import softspark.com.inventorypilot.users.data.mapper.toUserSync
import softspark.com.inventorypilot.users.data.repositories.UserRepository
import softspark.com.inventorypilot.users.remote.UserApi
import java.time.Duration
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val userApi: UserApi,
    private val userDao: UserDao,
    private val userProfileDao: UserProfileDao,
    private val networkUtils: NetworkUtils,
    private val workManager: WorkManager
) : UserRepository {
    override suspend fun addUser(userProfile: UserProfile) {
        userProfileDao.insertUser(userProfile.toEntity())

        resultOf {
            userApi.addUser(userProfile.toAddUserRequest(userProfile.id))
        }.onFailure {
            userDao.insertUserSync(userProfile.toUserSync())
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun syncUsers() {
        val worker = OneTimeWorkRequestBuilder<ProductSyncWorker>().setConstraints(
            Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        ).setBackoffCriteria(BackoffPolicy.EXPONENTIAL, Duration.ofMinutes(5))
            .build()

        workManager.beginUniqueWork("sync_users_id", ExistingWorkPolicy.REPLACE, worker)
            .enqueue()
    }
}
