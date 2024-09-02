package softspark.com.inventorypilot.users.domain.useCases

import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.users.data.repositories.UserRepository
import javax.inject.Inject

class AddUserUseCaseImpl @Inject constructor(private val userRepository: UserRepository) :
    AddUserUseCase {
    override suspend fun invoke(userProfile: UserProfile) = userRepository.addUser(userProfile)
}
