package softspark.com.inventorypilot.login.domain.useCases.authentication

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val authenticationRepository: AuthenticationRepository
) : LoginUseCase {
    override suspend fun invoke(email: String, password: String): Flow<Result<Unit>> {
        return authenticationRepository.login(email, password)
    }
}