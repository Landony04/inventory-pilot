package softspark.com.inventorypilot.users.domain.useCases

import softspark.com.inventorypilot.users.data.repositories.UserRepository
import javax.inject.Inject

class SyncUserUseCaseImpl @Inject constructor(
    private val userRepository: UserRepository
) : SyncUserUseCase {
    override suspend fun invoke() = userRepository.syncUsers()
}