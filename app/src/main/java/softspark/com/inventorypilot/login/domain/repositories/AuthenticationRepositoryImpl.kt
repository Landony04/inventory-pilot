package softspark.com.inventorypilot.login.domain.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository

class AuthenticationRepositoryImpl : AuthenticationRepository {
    override suspend fun login(email: String, password: String): Flow<Result<Unit>> {
        return flow {
            try {
                Firebase.auth.signInWithEmailAndPassword(email, password).await()
                emit(Result.success(Unit))
            } catch (exception: Exception) {
                emit(Result.failure(exception))
            }
        }
    }
}