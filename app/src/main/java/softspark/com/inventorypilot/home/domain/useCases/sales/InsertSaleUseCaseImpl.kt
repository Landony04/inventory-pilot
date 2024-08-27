package softspark.com.inventorypilot.home.domain.useCases.sales

import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import javax.inject.Inject

class InsertSaleUseCaseImpl @Inject constructor(
    private val salesRepository: SalesRepository
) : InsertSaleUseCase {
    override suspend fun invoke(sale: Sale) = salesRepository.insertSale(sale)
}