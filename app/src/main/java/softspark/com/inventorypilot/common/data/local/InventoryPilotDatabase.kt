package softspark.com.inventorypilot.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity
import softspark.com.inventorypilot.home.data.local.dao.cart.CartDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.local.entity.cart.CartItemEntity
import softspark.com.inventorypilot.home.data.local.entity.products.CategoryProductSyncEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductCategoryEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductSyncEntity
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleEntity
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleSyncEntity
import softspark.com.inventorypilot.home.data.local.typeconverter.SalesTypeConverters
import softspark.com.inventorypilot.users.data.local.dao.UserDao
import softspark.com.inventorypilot.users.data.local.entity.user.UserSyncEntity


@TypeConverters(value = [SalesTypeConverters::class])
@Database(
    entities = [UserProfileEntity::class, ProductCategoryEntity::class, ProductEntity::class, SaleEntity::class, CartItemEntity::class, SaleSyncEntity::class, ProductSyncEntity::class, UserSyncEntity::class, CategoryProductSyncEntity::class],
    version = 1,
    exportSchema = false
)
abstract class InventoryPilotDatabase : RoomDatabase() {
    abstract fun userProfileDao(): UserProfileDao

    abstract fun productCategoryDao(): ProductCategoryDao

    abstract fun productDao(): ProductDao

    abstract fun saleDao(): SalesDao

    abstract fun cartDao(): CartDao

    abstract fun userDao(): UserDao
}

val MIGRATION_1_2: Migration = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE UserProfileEntity ADD COLUMN cellPhone TEXT")
    }
}
