package softspark.com.inventorypilot.home.domain.useCases.profits

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result

interface GetDailyProfitsUseCase {

    suspend operator fun invoke(date: String): Flow<Result<Map<String, Double>>>
}
