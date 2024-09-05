package softspark.com.inventorypilot.home.domain.useCases.products

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.products.Product

interface GetProductByIdUseCase {
    suspend operator fun invoke(productId: String): Flow<Result<Product>>
}
