package softspark.com.inventorypilot.home.domain.useCases.session

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result

interface LogoutUseCase {
    suspend operator fun invoke(): Flow<Result<Boolean>>
}
