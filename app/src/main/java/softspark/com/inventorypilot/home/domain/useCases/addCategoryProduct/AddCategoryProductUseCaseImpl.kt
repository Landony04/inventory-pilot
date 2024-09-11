package softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct

import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import javax.inject.Inject

class AddCategoryProductUseCaseImpl @Inject constructor(
    private val productCategoryRepository: ProductCategoriesRepository
) : AddCategoryProductUseCase {
    override suspend fun invoke(productCategory: ProductCategory) =
        productCategoryRepository.addCategoryProduct(productCategory)
}
