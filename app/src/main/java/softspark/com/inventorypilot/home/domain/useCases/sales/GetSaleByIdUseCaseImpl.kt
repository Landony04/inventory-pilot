package softspark.com.inventorypilot.home.domain.useCases.sales

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import softspark.com.inventorypilot.home.domain.models.sales.SaleDetail
import javax.inject.Inject

class GetSaleByIdUseCaseImpl @Inject constructor(private val saleRepository: SalesRepository) :
    GetSaleByIdUseCase {
    override suspend fun invoke(saleId: String): Flow<Result<SaleDetail>> =
        saleRepository.getSaleById(saleId)
}