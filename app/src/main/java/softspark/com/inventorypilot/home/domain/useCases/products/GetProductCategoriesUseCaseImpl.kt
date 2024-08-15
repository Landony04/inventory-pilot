package softspark.com.inventorypilot.home.domain.useCases.products

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import javax.inject.Inject

class GetProductCategoriesUseCaseImpl @Inject constructor(
    private val productCategoriesRepository: ProductCategoriesRepository
) : GetProductCategoriesUseCase {
    override suspend fun invoke(): Flow<Result<ArrayList<ProductCategory>>> =
        productCategoriesRepository.getAllCategories()
}