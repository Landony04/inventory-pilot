package softspark.com.inventorypilot.home.domain.useCases.sales

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.sales.Sale

interface GetSalesUseCase {
    suspend operator fun invoke(): Flow<Result<ArrayList<Sale>>>
}
