package softspark.com.inventorypilot.login.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.login.data.matcher.EmailMatcherImpl
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import softspark.com.inventorypilot.login.domain.matcher.EmailMatcher
import softspark.com.inventorypilot.login.domain.repositories.AuthenticationRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(): AuthenticationRepository = AuthenticationRepositoryImpl()

    @Provides
    @Singleton
    fun provideEmailMatcher(): EmailMatcher = EmailMatcherImpl()
}