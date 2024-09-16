package softspark.com.inventorypilot.login.domain.useCases.authentication

import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import javax.inject.Inject

class GetBranchesUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : GetBranchesUseCase {
    override suspend fun invoke() = authenticationRepository.getBranches()
}
