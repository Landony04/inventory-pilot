package softspark.com.inventorypilot.home.domain.useCases.sales

import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import javax.inject.Inject

class SyncSalesUseCaseImpl @Inject constructor(
    private val salesRepository: SalesRepository
) : SyncSalesUseCase {
    override suspend fun invoke() = salesRepository.syncSales()
}