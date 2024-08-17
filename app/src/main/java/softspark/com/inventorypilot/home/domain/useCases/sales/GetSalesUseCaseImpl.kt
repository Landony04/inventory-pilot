package softspark.com.inventorypilot.home.domain.useCases.sales

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import javax.inject.Inject

class GetSalesUseCaseImpl @Inject constructor(
    private val salesRepository: SalesRepository
) : GetSalesUseCase {
    override suspend fun invoke(): Flow<Result<ArrayList<Sale>>> = salesRepository.getAllSales()
}
