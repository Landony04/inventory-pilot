package softspark.com.inventorypilot.home.data.repositories

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.products.Product

interface ProductsRepository {
    suspend fun getProductsForPage(
        page: Int,
        pageSize: Int
    ): Flow<Result<ArrayList<Product>>>

    suspend fun insertProducts(products: List<Product>)
}
