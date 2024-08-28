package softspark.com.inventorypilot.home.data.repositories

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity
import softspark.com.inventorypilot.home.domain.models.sales.ProductSale
import softspark.com.inventorypilot.home.domain.models.sales.Sale

interface SalesRepository {
    suspend fun getSalesForPage(
        page: Int,
        pageSize: Int
    ): Flow<Result<ArrayList<Sale>>>

    suspend fun getSalesByDate(
        date: String
    ): Flow<Result<ArrayList<Sale>>>

    suspend fun insertSales(sales: List<Sale>)

    suspend fun insertSale(sale: Sale)

    suspend fun syncSales()

    suspend fun updateProductsStock(products: List<ProductSale>)

    suspend fun getCurrentStockFromDataBase(productId: String): ProductEntity?
}
