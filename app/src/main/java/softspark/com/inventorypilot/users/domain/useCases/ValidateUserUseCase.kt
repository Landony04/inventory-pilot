package softspark.com.inventorypilot.users.domain.useCases

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.users.domain.entities.AddUserResult

interface ValidateUserUseCase {
    suspend operator fun invoke(
        email: String,
        firstName: String,
        lastName: String,
        role: String,
        cellPhone: String,
        branch: String
    ): Flow<AddUserResult>
}