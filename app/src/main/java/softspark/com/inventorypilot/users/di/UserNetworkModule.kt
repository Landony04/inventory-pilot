package softspark.com.inventorypilot.users.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import softspark.com.inventorypilot.BuildConfig
import softspark.com.inventorypilot.users.remote.UserApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserNetworkModule {

    @Singleton
    @Provides
    fun provideProductsApi(client: OkHttpClient): UserApi {
        return Retrofit.Builder().baseUrl(BuildConfig.FIREBASE_DATABASE_URL).client(client)
            .addConverterFactory(MoshiConverterFactory.create()).build()
            .create(UserApi::class.java)
    }
}