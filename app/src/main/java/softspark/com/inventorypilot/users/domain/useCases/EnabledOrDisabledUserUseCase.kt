package softspark.com.inventorypilot.users.domain.useCases

import softspark.com.inventorypilot.login.domain.models.UserProfile

interface EnabledOrDisabledUserUseCase {
    suspend operator fun invoke(user: UserProfile)
}
