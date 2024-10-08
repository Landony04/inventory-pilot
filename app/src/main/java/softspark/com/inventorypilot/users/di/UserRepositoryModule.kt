package softspark.com.inventorypilot.users.di

import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.data.local.dao.BranchDao
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.users.data.local.dao.UserDao
import softspark.com.inventorypilot.users.data.repositories.UserRepository
import softspark.com.inventorypilot.users.domain.repositories.UserRepositoryImpl
import softspark.com.inventorypilot.users.remote.UserApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserRepositoryModule {

    @Provides
    @Singleton
    fun provideUserRepository(
        dispatcherProvider: DispatcherProvider,
        userApi: UserApi,
        userDao: UserDao,
        branchDao: BranchDao,
        userProfileDao: UserProfileDao,
        workerManager: WorkManager
    ): UserRepository = UserRepositoryImpl(
        dispatcherProvider,
        userApi,
        userDao,
        branchDao,
        userProfileDao,
        workerManager
    )
}
