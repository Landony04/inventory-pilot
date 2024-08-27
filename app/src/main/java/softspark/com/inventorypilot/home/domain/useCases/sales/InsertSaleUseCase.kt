package softspark.com.inventorypilot.home.domain.useCases.sales

import softspark.com.inventorypilot.home.domain.models.sales.Sale

interface InsertSaleUseCase {
    suspend operator fun invoke(sale: Sale)
}