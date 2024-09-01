package softspark.com.inventorypilot.home.domain.useCases.addProduct

import softspark.com.inventorypilot.home.domain.models.products.Product

interface AddProductUseCase {
    suspend operator fun invoke(product: Product)
}