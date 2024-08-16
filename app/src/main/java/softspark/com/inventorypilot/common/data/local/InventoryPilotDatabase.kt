package softspark.com.inventorypilot.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.local.entity.products.ProductCategoryEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleEntity

@Database(
    entities = [UserProfileEntity::class, ProductCategoryEntity::class, ProductEntity::class, SaleEntity::class],
    version = 2,
    exportSchema = false
)
abstract class InventoryPilotDatabase() : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao

    abstract fun productCategoryDao(): ProductCategoryDao

    abstract fun productDao(): ProductDao

    abstract fun saleDao(): SalesDao
}
