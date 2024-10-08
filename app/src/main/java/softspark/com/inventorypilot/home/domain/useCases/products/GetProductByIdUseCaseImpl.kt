package softspark.com.inventorypilot.home.domain.useCases.products

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.models.products.Product
import javax.inject.Inject

class GetProductByIdUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : GetProductByIdUseCase {
    override suspend fun invoke(productId: String): Flow<Result<Product>> =
        productsRepository.getProductsById(productId = productId)
}
