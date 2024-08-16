package softspark.com.inventorypilot.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.entity.products.ProductCategoryEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity

@Database(
    entities = [UserProfileEntity::class, ProductCategoryEntity::class, ProductEntity::class],
    version = 2
)
abstract class InventoryPilotDatabase() : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao
    abstract fun productCategoryDao(): ProductCategoryDao

    abstract fun productDao(): ProductDao
}