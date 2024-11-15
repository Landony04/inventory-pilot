package softspark.com.inventorypilot.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import softspark.com.inventorypilot.BuildConfig
import softspark.com.inventorypilot.home.remote.ProductsApi
import softspark.com.inventorypilot.home.remote.SalesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeNetworkModule {

    @Singleton
    @Provides
    fun provideProductsApi(client: OkHttpClient): ProductsApi {
        return Retrofit.Builder().baseUrl(BuildConfig.FIREBASE_DATABASE_URL).client(client)
            .addConverterFactory(MoshiConverterFactory.create()).build()
            .create(ProductsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSalesApi(client: OkHttpClient): SalesApi {
        return Retrofit.Builder().baseUrl(BuildConfig.FIREBASE_DATABASE_URL).client(client)
            .addConverterFactory(MoshiConverterFactory.create()).build()
            .create(SalesApi::class.java)
    }
}


