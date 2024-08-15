package softspark.com.inventorypilot.login.domain.useCases.authentication

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import softspark.com.inventorypilot.login.domain.models.UserProfile
import javax.inject.Inject

class GetUserProfileUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : GetUserProfileUseCase {
    override suspend fun invoke(email: String): Flow<Result<UserProfile>> =
        authenticationRepository.getUserProfile(email)
}