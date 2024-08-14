package softspark.com.inventorypilot.login.domain.useCases.authentication

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.domain.models.UserProfile

interface GetUserProfileUseCase {
    suspend operator fun invoke(email: String): Flow<Result<UserProfile>>
}