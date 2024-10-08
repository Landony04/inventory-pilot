package softspark.com.inventorypilot.login.domain.useCases.authentication

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result

interface LoginUseCase {
    suspend operator fun invoke(email: String, password: String): Flow<Result<Unit>>
}