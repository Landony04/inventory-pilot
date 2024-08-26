package softspark.com.inventorypilot.home.data.local.dao.products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productCategory: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(productCategories: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity WHERE productId = :id")
    fun getProductById(id: String): ProductEntity

    @Query("SELECT * FROM ProductEntity ORDER BY productId ASC")
    fun getAllProducts(): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity ORDER BY productId ASC LIMIT :limit OFFSET :offset")
    fun getProductsForPage(limit: Int, offset: Int): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE categoryId = :categoryId ORDER BY productId ASC")
    fun getProductsByCategoryId(categoryId: String): Flow<List<ProductEntity>>

    @Query("SELECT * FROM ProductEntity WHERE name LIKE :query || '%' ORDER BY productId ASC")
    fun getProductsByName(query: String): Flow<List<ProductEntity>>
}
