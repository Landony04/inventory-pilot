package softspark.com.inventorypilot.login.data.repositories

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.domain.models.Branch
import softspark.com.inventorypilot.login.domain.models.UserProfile

interface AuthenticationRepository {

    suspend fun getUserProfile(email: String): Flow<Result<UserProfile>>

    suspend fun insertUsers(users: List<UserProfile>)

    suspend fun login(email: String, password: String): Flow<Result<Unit>>

    suspend fun getBranches()

    suspend fun insertBranches(branches: List<Branch>)
}
