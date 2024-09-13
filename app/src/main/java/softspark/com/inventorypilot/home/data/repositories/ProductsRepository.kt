package softspark.com.inventorypilot.home.data.repositories

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.products.Product

interface ProductsRepository {

    suspend fun addProduct(
        product: Product
    )

    suspend fun getProductsForPage(): Flow<List<Product>>

    suspend fun getProductsByCategoryId(
        categoryId: String
    ): List<Product>

    suspend fun getProductsByName(
        query: String
    ): List<Product>

    suspend fun getProductsById(productId: String): Flow<Result<Product>>

    suspend fun insertProducts(products: List<Product>)

    suspend fun syncProducts()

    suspend fun updateProduct(product: Product)
}
