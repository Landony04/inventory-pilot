package softspark.com.inventorypilot.home.domain.useCases.products

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.models.products.Product
import javax.inject.Inject

class GetProductsByCategoryIdUseCaseImpl @Inject constructor(private val productsRepository: ProductsRepository) :
    GetProductsByCategoryIdUseCase {
    override suspend fun invoke(categoryId: String): Flow<Result<ArrayList<Product>>> =
        productsRepository.getProductsByCategoryId(categoryId)
}