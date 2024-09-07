package softspark.com.inventorypilot.common.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import softspark.com.inventorypilot.common.domain.useCases.GenerateCurrentDateUTCUseCase
import softspark.com.inventorypilot.common.domain.useCases.GenerateCurrentDateUTCUseCaseImpl
import softspark.com.inventorypilot.common.domain.useCases.GenerateIdUseCase
import softspark.com.inventorypilot.common.domain.useCases.GenerateIdUseCaseImpl
import softspark.com.inventorypilot.common.domain.useCases.GetUserIdUseCase
import softspark.com.inventorypilot.common.domain.useCases.GetUserIdUseCaseImpl
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences

@Module
@InstallIn(ViewModelComponent::class)
object CommonUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetUserIdUseCase(
        inventoryPilotPreferences: InventoryPilotPreferences
    ): GetUserIdUseCase = GetUserIdUseCaseImpl(inventoryPilotPreferences)

    @ViewModelScoped
    @Provides
    fun provideGenerateIdUseCase(): GenerateIdUseCase = GenerateIdUseCaseImpl()

    @ViewModelScoped
    @Provides
    fun provideGenerateCurrentDateUTCUseCase(): GenerateCurrentDateUTCUseCase =
        GenerateCurrentDateUTCUseCaseImpl()
}
