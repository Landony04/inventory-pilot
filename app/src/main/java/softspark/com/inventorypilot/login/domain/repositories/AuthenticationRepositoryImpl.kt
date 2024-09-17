package softspark.com.inventorypilot.login.domain.repositories

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.data.local.dao.BranchDao
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.NetworkUtils
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_BRANCH_ID_PREFERENCE
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_ID_PREFERENCE
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_ROLE_PREFERENCE
import softspark.com.inventorypilot.login.data.mapper.toBranchEntity
import softspark.com.inventorypilot.login.data.mapper.toBranchesDomain
import softspark.com.inventorypilot.login.data.mapper.toDomain
import softspark.com.inventorypilot.login.data.mapper.toEntity
import softspark.com.inventorypilot.login.data.mapper.toUserProfile
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import softspark.com.inventorypilot.login.domain.models.Branch
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.remote.LoginApi
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val userDao: UserProfileDao,
    private val branchDao: BranchDao,
    private val dispatchers: DispatcherProvider,
    private val firebaseAuth: FirebaseAuth,
    private val loginApi: LoginApi,
    private val networkUtils: NetworkUtils,
    private val inventoryPilotPreferences: InventoryPilotPreferences
) : AuthenticationRepository {

    override suspend fun getUserProfile(email: String): Flow<Result<UserProfile>> =
        flow<Result<UserProfile>> {

            if (networkUtils.isInternetAvailable()) {
                val userProfile = loginApi.getUserProfile().toDomain()
                insertUsers(userProfile)
            }

            val user = userDao.getUserProfileByEmail(email).toUserProfile()

            inventoryPilotPreferences.setValuesString(USER_ID_PREFERENCE, user.id)
            inventoryPilotPreferences.setValuesString(USER_ROLE_PREFERENCE, user.role)
            inventoryPilotPreferences.setValuesString(USER_BRANCH_ID_PREFERENCE, user.branchId)
            emit(Result.Success(data = user))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun insertUsers(users: List<UserProfile>) =
        withContext(dispatchers.io()) {
            userDao.insertUsers(users.map { user -> async { user.toEntity() }.await() })
        }

    override suspend fun login(email: String, password: String): Flow<Result<Unit>> =
        flow<Result<Unit>> {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun getBranches() = withContext(dispatchers.io()) {
        val branches = loginApi.getBranches().toBranchesDomain()

        insertBranches(branches)
    }

    override suspend fun insertBranches(branches: List<Branch>) = withContext(dispatchers.io()) {
        branchDao.insertBranch(branches.map { branch -> async { branch.toBranchEntity() }.await() })
    }
}