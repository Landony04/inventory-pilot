package softspark.com.inventorypilot.login.domain.useCases.authentication

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.login.domain.entities.PasswordResult

interface ValidatePasswordUseCase {
    operator fun invoke(password: String): Flow<PasswordResult>
}