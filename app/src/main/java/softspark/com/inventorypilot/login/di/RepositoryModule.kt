package softspark.com.inventorypilot.login.di

import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.login.data.matcher.EmailMatcherImpl
import softspark.com.inventorypilot.login.data.repositories.AuthenticationRepository
import softspark.com.inventorypilot.login.domain.matcher.EmailMatcher
import softspark.com.inventorypilot.login.domain.repositories.AuthenticationRepositoryImpl
import softspark.com.inventorypilot.login.remote.LoginApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthenticationRepository(
        dao: UserProfileDao,
        firebaseAuth: FirebaseAuth,
        loginApi: LoginApi
    ): AuthenticationRepository =
        AuthenticationRepositoryImpl(dao, firebaseAuth, loginApi)

    @Provides
    @Singleton
    fun provideEmailMatcher(): EmailMatcher = EmailMatcherImpl()
}