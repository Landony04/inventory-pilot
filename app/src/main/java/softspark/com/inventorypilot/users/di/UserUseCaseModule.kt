package softspark.com.inventorypilot.users.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import softspark.com.inventorypilot.users.data.repositories.UserRepository
import softspark.com.inventorypilot.users.domain.useCases.AddUserUseCase
import softspark.com.inventorypilot.users.domain.useCases.AddUserUseCaseImpl
import softspark.com.inventorypilot.users.domain.useCases.ValidateUserUseCase
import softspark.com.inventorypilot.users.domain.useCases.ValidateUserUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object UserUseCaseModule {

    //USE CASES FOR ADD USER
    @ViewModelScoped
    @Provides
    fun provideAddUserUseCase(
        userRepository: UserRepository
    ): AddUserUseCase = AddUserUseCaseImpl(userRepository)

    @ViewModelScoped
    @Provides
    fun provideValidateUserUseCase(
        @ApplicationContext context: Context
    ): ValidateUserUseCase = ValidateUserUseCaseImpl(context)
}