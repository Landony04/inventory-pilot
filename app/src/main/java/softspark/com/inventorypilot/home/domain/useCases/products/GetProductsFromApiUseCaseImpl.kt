package softspark.com.inventorypilot.home.domain.useCases.products

import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import javax.inject.Inject

class GetProductsFromApiUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : GetProductsFromApiUseCase {
    override suspend fun invoke() = productsRepository.getProductsFromApi()
}
