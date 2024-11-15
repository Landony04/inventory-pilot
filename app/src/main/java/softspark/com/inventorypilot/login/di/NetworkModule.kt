package softspark.com.inventorypilot.login.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import softspark.com.inventorypilot.BuildConfig
import softspark.com.inventorypilot.login.remote.LoginApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideAuth(): FirebaseAuth = Firebase.auth

    @Singleton
    @Provides
    fun provideLoginApi(client: OkHttpClient): LoginApi {
        return Retrofit.Builder().baseUrl(BuildConfig.FIREBASE_DATABASE_URL).client(client)
            .addConverterFactory(MoshiConverterFactory.create()).build()
            .create(LoginApi::class.java)
    }
}
