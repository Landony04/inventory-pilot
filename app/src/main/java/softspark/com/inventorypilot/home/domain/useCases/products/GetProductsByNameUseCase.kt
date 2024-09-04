package softspark.com.inventorypilot.home.domain.useCases.products

import softspark.com.inventorypilot.home.domain.models.products.Product

interface GetProductsByNameUseCase {
    suspend operator fun invoke(query: String): List<Product>
}