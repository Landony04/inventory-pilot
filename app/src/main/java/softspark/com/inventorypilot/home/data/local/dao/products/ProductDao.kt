package softspark.com.inventorypilot.home.data.local.dao.products

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductSyncEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productCategory: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(productCategories: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity WHERE productId = :id")
    fun getProductById(id: String): ProductEntity

    @Query("SELECT * FROM ProductEntity ORDER BY name ASC LIMIT :limit OFFSET :offset")
    suspend fun getProductsForPage(limit: Int, offset: Int): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE categoryId = :categoryId ORDER BY name ASC")
    fun getProductsByCategoryId(categoryId: String): List<ProductEntity>

    @Query("SELECT * FROM ProductEntity WHERE name LIKE :query || '%' ORDER BY name ASC")
    fun getProductsByName(query: String): List<ProductEntity>

    @Update
    suspend fun updateProduct(productEntity: ProductEntity)

    @Query("SELECT * FROM ProductSyncEntity")
    fun getAllProductSync(): List<ProductSyncEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProductSync(productSyncEntity: ProductSyncEntity)

    @Delete
    suspend fun deleteProductSync(productSyncEntity: ProductSyncEntity)
}
