package softspark.com.inventorypilot.common.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.data.local.InventoryPilotDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        InventoryPilotDatabase::class.java,
        "inventory_pilot_db"
    ).build()


    @Provides
    @Singleton
    fun provideUserProfileDao(inventoryPilotDatabase: InventoryPilotDatabase) =
        inventoryPilotDatabase.userProfileDao()

    @Provides
    @Singleton
    fun provideProductCategoryDao(inventoryPilotDatabase: InventoryPilotDatabase) =
        inventoryPilotDatabase.productCategoryDao()

    @Provides
    @Singleton
    fun provideProductDao(inventoryPilotDatabase: InventoryPilotDatabase) =
        inventoryPilotDatabase.productDao()

    @Provides
    @Singleton
    fun provideSaleDao(inventoryPilotDatabase: InventoryPilotDatabase) =
        inventoryPilotDatabase.saleDao()

    @Provides
    @Singleton
    fun provideCartDao(inventoryPilotDatabase: InventoryPilotDatabase) =
        inventoryPilotDatabase.cartDao()

    @Provides
    @Singleton
    fun provideUserDao(inventoryPilotDatabase: InventoryPilotDatabase) =
        inventoryPilotDatabase.userDao()
}
