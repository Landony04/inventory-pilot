package softspark.com.inventorypilot.users.domain.useCases

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.users.data.repositories.UserRepository
import javax.inject.Inject

class GetAllUsersUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : GetAllUsersUseCase {
    override suspend fun invoke(): Flow<Result<List<UserProfile>>> = userRepository.getAllUsers()
}