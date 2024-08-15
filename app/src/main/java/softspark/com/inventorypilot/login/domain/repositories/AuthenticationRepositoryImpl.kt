package softspark.com.inventorypilot.login.domain.repositories

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.tasks.await
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.data.mapper.toDomain
import softspark.com.inventorypilot.login.data.mapper.toEntity
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.remote.LoginApi
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val dao: UserProfileDao,
    private val dispatchers: DispatcherProvider,
    private val firebaseAuth: FirebaseAuth,
    private val loginApi: LoginApi
) : AuthenticationRepository {
    override suspend fun getUserProfile(email: String): Flow<Result<UserProfile>> =
        flow<Result<UserProfile>> {
            val userProfile = loginApi.getUserProfile().toDomain(email)
            dao.insertUserProfile(userProfile.toEntity())
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun login(email: String, password: String): Flow<Result<Unit>> =
        flow<Result<Unit>> {
            firebaseAuth.signInWithEmailAndPassword(email, password).await()
            emit(Result.Success(Unit))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())
}