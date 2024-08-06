package softspark.com.inventorypilot.login.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import softspark.com.inventorypilot.login.domain.repositories.AuthenticationRepositoryImpl
import softspark.com.inventorypilot.login.domain.useCases.authentication.LoginUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.LoginUseCaseImpl
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object LoginModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(): AuthenticationRepository = AuthenticationRepositoryImpl()

    @Provides
    @ViewModelScoped
    @Singleton
    fun provideLoginUseCase(
        authenticationRepository: AuthenticationRepository
    ): LoginUseCase = LoginUseCaseImpl(authenticationRepository)
}