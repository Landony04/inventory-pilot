package softspark.com.inventorypilot.login.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import softspark.com.inventorypilot.login.domain.matcher.EmailMatcher
import softspark.com.inventorypilot.login.domain.useCases.authentication.LoginUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.LoginUseCaseImpl
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidateEmailUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidateEmailUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideLoginUseCase(
        authenticationRepository: AuthenticationRepository
    ): LoginUseCase = LoginUseCaseImpl(authenticationRepository)

    @ViewModelScoped
    @Provides
    fun provideValidateEmailUseCase(
        emailMatcher: EmailMatcher
    ): ValidateEmailUseCase = ValidateEmailUseCaseImpl(emailMatcher)
}