package softspark.com.inventorypilot.home.data.local.dao.products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(productCategory: ProductEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(productCategories: List<ProductEntity>)

    @Query("SELECT * FROM ProductEntity WHERE id = :id")
    fun getProductById(id: String): ProductEntity

    @Query("SELECT * FROM ProductEntity")
    fun getAllProducts(): List<ProductEntity>
}