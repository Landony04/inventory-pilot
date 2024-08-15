package softspark.com.inventorypilot.home.data.repositories

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory

interface ProductCategoriesRepository {
    suspend fun getAllCategories(): Flow<Result<ArrayList<ProductCategory>>>

    suspend fun getCategoryById(id: String): Flow<Result<ProductCategory>>

    suspend fun insertProductCategory(productCategory: ProductCategory): Flow<Result<Boolean>>

    suspend fun insertProductCategories(productCategories: List<ProductCategory>)
}
