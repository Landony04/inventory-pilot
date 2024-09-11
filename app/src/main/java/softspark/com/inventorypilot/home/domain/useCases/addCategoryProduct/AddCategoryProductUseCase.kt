package softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct

import softspark.com.inventorypilot.home.domain.models.products.ProductCategory

interface AddCategoryProductUseCase {
    suspend operator fun invoke(productCategory: ProductCategory)
}
