package softspark.com.inventorypilot.common.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilder
import softspark.com.inventorypilot.common.utils.dialogs.DialogBuilderImpl
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CommonModule {

    @Singleton
    @Provides
    fun provideDialogBuilder(): DialogBuilder = DialogBuilderImpl()

    @Singleton
    @Provides
    fun providePreferences(
        @ApplicationContext context: Context
    ): InventoryPilotPreferences = InventoryPilotPreferencesImpl(context)
}