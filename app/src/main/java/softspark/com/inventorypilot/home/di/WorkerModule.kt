package softspark.com.inventorypilot.home.di

import androidx.work.WorkerFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.home.data.sync.CustomWorkerFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)

object WorkerModule {

    @Provides
    @Singleton
    fun provideWorkerFactory(
        customWorkerFactory: CustomWorkerFactory
    ): WorkerFactory {
        return customWorkerFactory
    }
}