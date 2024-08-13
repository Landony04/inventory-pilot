package softspark.com.inventorypilot.splash.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.splash.data.repositories.MainRepository
import softspark.com.inventorypilot.splash.domain.repositories.MainRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(): MainRepository = MainRepositoryImpl()
}