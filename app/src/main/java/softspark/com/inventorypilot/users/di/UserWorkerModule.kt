package softspark.com.inventorypilot.users.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.users.data.local.dao.UserDao
import softspark.com.inventorypilot.users.data.sync.CustomWorkerFactoryUser
import softspark.com.inventorypilot.users.remote.UserApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserWorkerModule {

    @Provides
    @Singleton
    fun provideUserWorkerFactory(
        userApi: UserApi,
        userDao: UserDao,
        userProfileDao: UserProfileDao
    ): CustomWorkerFactoryUser = CustomWorkerFactoryUser(
        userApi = userApi,
        userDao = userDao,
        userProfileDao = userProfileDao
    )
}