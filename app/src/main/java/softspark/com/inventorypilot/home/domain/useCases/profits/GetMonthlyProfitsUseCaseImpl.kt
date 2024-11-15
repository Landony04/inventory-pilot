package softspark.com.inventorypilot.home.domain.useCases.profits

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import javax.inject.Inject

class GetMonthlyProfitsUseCaseImpl @Inject constructor(
    private val salesRepository: SalesRepository
) : GetMonthlyProfitsUseCase {
    override suspend fun invoke(month: String): Flow<Result<Map<String, Double>>> =
        salesRepository.getMonthlyProfits(month)
}
