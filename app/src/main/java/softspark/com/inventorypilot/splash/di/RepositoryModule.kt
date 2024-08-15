package softspark.com.inventorypilot.splash.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.splash.data.repositories.MainRepository
import softspark.com.inventorypilot.splash.domain.repositories.MainRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(dispatcherProvider: DispatcherProvider): MainRepository =
        MainRepositoryImpl(dispatcherProvider)
}