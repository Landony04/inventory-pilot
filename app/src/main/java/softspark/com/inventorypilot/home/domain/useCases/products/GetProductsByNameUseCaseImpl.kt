package softspark.com.inventorypilot.home.domain.useCases.products

import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.models.products.Product
import javax.inject.Inject

class GetProductsByNameUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : GetProductsByNameUseCase {
    override suspend fun invoke(query: String): List<Product> =
        productsRepository.getProductsByName(query)
}