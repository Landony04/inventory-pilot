package softspark.com.inventorypilot.home.domain.useCases.products

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.home.domain.models.products.Product

interface GetProductsUseCase {
    suspend operator fun invoke(): Flow<List<Product>>
}
