package softspark.com.inventorypilot.home.di

import android.content.Context
import androidx.work.WorkManager
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.data.local.InventoryPilotDatabase
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.utils.NetworkUtils
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.home.data.local.dao.cart.CartDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import softspark.com.inventorypilot.home.data.repositories.SessionRepository
import softspark.com.inventorypilot.home.domain.repositories.cart.CartRepositoryImpl
import softspark.com.inventorypilot.home.domain.repositories.products.ProductCategoriesRepositoryImpl
import softspark.com.inventorypilot.home.domain.repositories.products.ProductsRepositoryImpl
import softspark.com.inventorypilot.home.domain.repositories.sales.SalesRepositoryImpl
import softspark.com.inventorypilot.home.domain.repositories.session.SessionRepositoryImpl
import softspark.com.inventorypilot.home.remote.ProductsApi
import softspark.com.inventorypilot.home.remote.SalesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {
    @Provides
    @Singleton
    fun provideProductCategoriesRepository(
        preferences: InventoryPilotPreferences,
        dispatcherProvider: DispatcherProvider,
        productsApi: ProductsApi,
        productCategoryDao: ProductCategoryDao,
        networkUtils: NetworkUtils,
        workManager: WorkManager
    ): ProductCategoriesRepository =
        ProductCategoriesRepositoryImpl(
            preferences,
            dispatcherProvider,
            productsApi,
            productCategoryDao,
            networkUtils,
            workManager
        )

    @Provides
    @Singleton
    fun provideProductsRepository(
        preferences: InventoryPilotPreferences,
        dispatcherProvider: DispatcherProvider,
        productsApi: ProductsApi,
        productDao: ProductDao,
        networkUtils: NetworkUtils,
        workManager: WorkManager
    ): ProductsRepository =
        ProductsRepositoryImpl(
            preferences,
            dispatcherProvider,
            productsApi,
            productDao,
            networkUtils,
            workManager
        )

    @Provides
    @Singleton
    fun provideSalesRepository(
        preferences: InventoryPilotPreferences,
        dispatcherProvider: DispatcherProvider,
        productDao: ProductDao,
        salesApi: SalesApi,
        salesDao: SalesDao,
        userProfileDao: UserProfileDao,
        networkUtils: NetworkUtils,
        workManager: WorkManager
    ): SalesRepository =
        SalesRepositoryImpl(
            preferences,
            dispatcherProvider,
            networkUtils,
            productDao,
            salesApi,
            salesDao,
            userProfileDao,
            workManager
        )

    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideCartRepository(
        dispatcherProvider: DispatcherProvider,
        cartDao: CartDao
    ): CartRepository = CartRepositoryImpl(cartDao, dispatcherProvider)

    @Provides
    @Singleton
    fun provideSessionRepository(
        dispatcherProvider: DispatcherProvider,
        firebaseAuth: FirebaseAuth,
        inventoryPilotDatabase: InventoryPilotDatabase
    ): SessionRepository =
        SessionRepositoryImpl(dispatcherProvider, firebaseAuth, inventoryPilotDatabase)
}
