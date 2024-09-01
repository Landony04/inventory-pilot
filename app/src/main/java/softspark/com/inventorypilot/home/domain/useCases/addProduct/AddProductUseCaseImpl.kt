package softspark.com.inventorypilot.home.domain.useCases.addProduct

import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.models.products.Product
import javax.inject.Inject

class AddProductUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : AddProductUseCase {
    override suspend fun invoke(product: Product) = productsRepository.addProduct(product)
}