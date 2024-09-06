package softspark.com.inventorypilot.home.domain.useCases.products

import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.models.products.Product
import javax.inject.Inject

class UpdateProductUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : UpdateProductUseCase {
    override suspend fun invoke(product: Product) = productsRepository.updateProduct(product)
}
