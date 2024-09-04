package softspark.com.inventorypilot.home.domain.useCases.products

import softspark.com.inventorypilot.home.domain.models.products.Product

interface GetProductsByCategoryIdUseCase {
    suspend operator fun invoke(categoryId: String): List<Product>
}