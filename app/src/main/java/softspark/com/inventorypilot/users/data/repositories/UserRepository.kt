package softspark.com.inventorypilot.users.data.repositories

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.domain.models.UserProfile

interface UserRepository {
    suspend fun addUser(userProfile: UserProfile)

    suspend fun getAllUsers(): Flow<Result<List<UserProfile>>>

    suspend fun syncUsers()
}