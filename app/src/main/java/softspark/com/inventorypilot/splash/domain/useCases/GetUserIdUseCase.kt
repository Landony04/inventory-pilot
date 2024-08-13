package softspark.com.inventorypilot.splash.domain.useCases

import kotlinx.coroutines.flow.Flow

interface GetUserIdUseCase {
    operator fun invoke(): Flow<String?>
}