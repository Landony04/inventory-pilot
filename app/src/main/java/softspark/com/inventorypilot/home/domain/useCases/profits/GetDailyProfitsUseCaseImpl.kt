package softspark.com.inventorypilot.home.domain.useCases.profits

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import javax.inject.Inject

class GetDailyProfitsUseCaseImpl @Inject constructor(
    private val salesRepository: SalesRepository
) : GetDailyProfitsUseCase {

    override suspend fun invoke(date: String): Flow<Result<Map<String, Double>>> =
        salesRepository.getDailyProfits(date = date)
}
