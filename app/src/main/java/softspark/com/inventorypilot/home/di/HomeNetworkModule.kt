package softspark.com.inventorypilot.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import softspark.com.inventorypilot.common.data.util.DefaultDispatcherProvider
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.home.remote.ProductsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeNetworkModule {

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider {
        return DefaultDispatcherProvider()
    }

    @Singleton
    @Provides
    fun provideProductsApi(client: OkHttpClient): ProductsApi {
        return Retrofit.Builder().baseUrl(ProductsApi.BASE_URL).client(client)
            .addConverterFactory(MoshiConverterFactory.create()).build()
            .create(ProductsApi::class.java)
    }
}


