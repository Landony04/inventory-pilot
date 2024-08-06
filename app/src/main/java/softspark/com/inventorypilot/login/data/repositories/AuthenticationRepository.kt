package softspark.com.inventorypilot.login.data.repositories

import kotlinx.coroutines.flow.Flow

interface AuthenticationRepository {
    suspend fun login(email: String, password: String): Flow<Result<Unit>>
}