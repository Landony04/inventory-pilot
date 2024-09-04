package softspark.com.inventorypilot.home.domain.useCases.products

import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.models.products.Product
import javax.inject.Inject

class GetProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : GetProductsUseCase {
    override suspend fun invoke(
        limit: Int,
        offset: Int
    ): List<Product> = productsRepository.getProductsForPage(limit, offset)
}
