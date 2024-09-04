package softspark.com.inventorypilot.home.domain.useCases.products

import softspark.com.inventorypilot.home.domain.models.products.Product

interface GetProductsUseCase {
    suspend operator fun invoke(
        limit: Int,
        offset: Int
    ): List<Product>
}
