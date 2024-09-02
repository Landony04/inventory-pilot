package softspark.com.inventorypilot.users.data.repositories

import softspark.com.inventorypilot.login.domain.models.UserProfile

interface UserRepository {
    suspend fun addUser(userProfile: UserProfile)
}