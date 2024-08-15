package softspark.com.inventorypilot.login.domain.repositories

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.data.mapper.toDomain
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.remote.LoginApi
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val loginApi: LoginApi
) : AuthenticationRepository {
    override suspend fun getUserProfile(email: String): Flow<Result<UserProfile>> {
        return flow {
            try {
                val userProfile = loginApi.getUserProfile().toDomain(email)
                emit(Result.Success(userProfile))
            } catch (exception: Exception) {
                emit(Result.Error(exception))
            }
        }
    }

    override suspend fun login(email: String, password: String): Flow<Result<Unit>> {
        return flow {
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).await()
                emit(Result.Success(Unit))
            } catch (exception: Exception) {
                emit(Result.Error(exception))
            }
        }
    }
}