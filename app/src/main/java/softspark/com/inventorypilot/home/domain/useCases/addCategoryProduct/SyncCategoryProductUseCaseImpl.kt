package softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct

import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import javax.inject.Inject

class SyncCategoryProductUseCaseImpl @Inject constructor(
    private val productCategoryRepository: ProductCategoriesRepository
) : SyncCategoryProductUseCase {
    override suspend fun invoke() = productCategoryRepository.syncCategoryProduct()
}
