package softspark.com.inventorypilot.users.domain.useCases

import softspark.com.inventorypilot.login.domain.models.UserProfile

interface AddUserUseCase {
    suspend operator fun invoke(userProfile: UserProfile)
}
