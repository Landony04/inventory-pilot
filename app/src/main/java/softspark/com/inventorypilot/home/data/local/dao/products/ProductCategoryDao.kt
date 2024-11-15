package softspark.com.inventorypilot.home.data.local.dao.products

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.home.data.local.entity.products.CategoryProductSyncEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductCategoryEntity

@Dao
interface ProductCategoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory(productCategory: ProductCategoryEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategories(productCategories: List<ProductCategoryEntity>)

    @Query("SELECT * FROM ProductCategoryEntity WHERE categoryId = :id")
    fun getCategoryById(id: String): ProductCategoryEntity

    @Query("SELECT * FROM ProductCategoryEntity")
    fun getProductCategories(): Flow<List<ProductCategoryEntity>>

    @Query("SELECT * FROM CategoryProductSyncEntity")
    fun getAllCategoriesProductSync(): List<CategoryProductSyncEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategoryProductSync(categoryProductSyncEntity: CategoryProductSyncEntity)

    @Delete
    suspend fun deleteCategoryProductSync(categoryProductSyncEntity: CategoryProductSyncEntity)
}