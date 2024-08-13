package softspark.com.inventorypilot.splash.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import softspark.com.inventorypilot.splash.data.repositories.MainRepository
import softspark.com.inventorypilot.splash.domain.useCases.GetUserIdUseCase
import softspark.com.inventorypilot.splash.domain.useCases.GetUserIdUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetUserIdUseCase(
        mainRepository: MainRepository
    ): GetUserIdUseCase = GetUserIdUseCaseImpl(mainRepository)
}