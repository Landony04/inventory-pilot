package softspark.com.inventorypilot.home.domain.useCases.profits

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result

interface GetMonthlyProfitsUseCase {

    suspend operator fun invoke(month: String): Flow<Result<Map<String, Double>>>
}
