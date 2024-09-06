package softspark.com.inventorypilot.home.domain.useCases.products

import softspark.com.inventorypilot.home.domain.models.products.Product

interface UpdateProductUseCase {
    suspend operator fun invoke(product: Product)
}
