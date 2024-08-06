package softspark.com.inventorypilot.login.domain.useCases.authentication

import kotlinx.coroutines.flow.Flow

interface ValidateEmailUseCase {
    operator fun invoke(email: String): Flow<Boolean>
}