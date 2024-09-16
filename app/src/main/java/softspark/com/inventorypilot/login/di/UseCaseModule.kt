package softspark.com.inventorypilot.login.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import softspark.com.inventorypilot.login.domain.matcher.EmailMatcher
import softspark.com.inventorypilot.login.domain.useCases.authentication.GetBranchesUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.GetBranchesUseCaseImpl
import softspark.com.inventorypilot.login.domain.useCases.authentication.GetUserProfileUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.GetUserProfileUseCaseImpl
import softspark.com.inventorypilot.login.domain.useCases.authentication.LoginUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.LoginUseCaseImpl
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidateEmailUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidateEmailUseCaseImpl
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidatePasswordUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidatePasswordUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetUserProfileUseCase(
        authenticationRepository: AuthenticationRepository
    ): GetUserProfileUseCase = GetUserProfileUseCaseImpl(authenticationRepository)

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

    @ViewModelScoped
    @Provides
    fun provideValidatePasswordUseCase(
        @ApplicationContext context: Context
    ): ValidatePasswordUseCase = ValidatePasswordUseCaseImpl(context)

    @ViewModelScoped
    @Provides
    fun provideGetBranchesUseCase(
        authenticationRepository: AuthenticationRepository
    ): GetBranchesUseCase = GetBranchesUseCaseImpl(authenticationRepository)
}