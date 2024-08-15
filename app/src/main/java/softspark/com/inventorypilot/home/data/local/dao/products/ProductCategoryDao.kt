package softspark.com.inventorypilot.home.data.local.dao.products

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import softspark.com.inventorypilot.home.data.local.entity.products.ProductCategoryEntity

@Dao
interface ProductCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(productCategory: ProductCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(productCategories: List<ProductCategoryEntity>)

    @Query("SELECT * FROM product_category WHERE id = :id")
    fun getCategoryById(id: String): ProductCategoryEntity

    @Query("SELECT * FROM product_category")
    fun getProductCategories(): List<ProductCategoryEntity>
}