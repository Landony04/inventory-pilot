package softspark.com.inventorypilot.common.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.data.local.InventoryPilotDatabase
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {

    @Singleton
    @Provides
    fun provideUserProfileDao(@ApplicationContext context: Context): UserProfileDao {
        return Room.databaseBuilder(
            context,
            InventoryPilotDatabase::class.java,
            "inventory_pilot_db"
        ).build().dao
    }
}