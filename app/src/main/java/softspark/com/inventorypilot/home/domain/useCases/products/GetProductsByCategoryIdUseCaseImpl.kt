package softspark.com.inventorypilot.home.domain.useCases.products

import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.models.products.Product
import javax.inject.Inject

class GetProductsByCategoryIdUseCaseImpl @Inject constructor(private val productsRepository: ProductsRepository) :
    GetProductsByCategoryIdUseCase {
    override suspend fun invoke(categoryId: String): List<Product> =
        productsRepository.getProductsByCategoryId(categoryId)
}