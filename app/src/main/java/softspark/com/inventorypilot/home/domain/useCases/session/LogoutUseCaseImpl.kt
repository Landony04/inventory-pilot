package softspark.com.inventorypilot.home.domain.useCases.session

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.SessionRepository
import javax.inject.Inject

class LogoutUseCaseImpl @Inject constructor(
    private val sessionRepository: SessionRepository
) : LogoutUseCase {
    override suspend fun invoke(): Flow<Result<Boolean>> = sessionRepository.logout()
}
