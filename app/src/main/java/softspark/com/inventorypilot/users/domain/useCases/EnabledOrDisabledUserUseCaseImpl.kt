package softspark.com.inventorypilot.users.domain.useCases

import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.users.data.repositories.UserRepository
import javax.inject.Inject

class EnabledOrDisabledUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : EnabledOrDisabledUserUseCase {
    override suspend fun invoke(user: UserProfile) = userRepository.enabledOrDisabledUser(user)
}
