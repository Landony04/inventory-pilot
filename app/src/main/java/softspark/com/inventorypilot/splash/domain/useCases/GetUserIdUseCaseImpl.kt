package softspark.com.inventorypilot.splash.domain.useCases

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.splash.data.repositories.MainRepository
import javax.inject.Inject

class GetUserIdUseCaseImpl @Inject constructor(
    private val mainRepository: MainRepository
) : GetUserIdUseCase {
    override fun invoke(): Flow<String?> = mainRepository.getUserId()
}
