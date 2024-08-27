package softspark.com.inventorypilot.home.di

import android.content.Context
import androidx.work.WorkManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.utils.NetworkUtils
import softspark.com.inventorypilot.home.data.local.dao.cart.CartDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import softspark.com.inventorypilot.home.domain.repositories.cart.CartRepositoryImpl
import softspark.com.inventorypilot.home.domain.repositories.products.ProductCategoriesRepositoryImpl
import softspark.com.inventorypilot.home.domain.repositories.products.ProductsRepositoryImpl
import softspark.com.inventorypilot.home.domain.repositories.sales.SalesRepositoryImpl
import softspark.com.inventorypilot.home.remote.ProductsApi
import softspark.com.inventorypilot.home.remote.SalesApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {
    @Provides
    @Singleton
    fun provideProductCategoriesRepository(
        dispatcherProvider: DispatcherProvider,
        productsApi: ProductsApi,
        productCategoryDao: ProductCategoryDao,
        networkUtils: NetworkUtils
    ): ProductCategoriesRepository =
        ProductCategoriesRepositoryImpl(
            dispatcherProvider,
            productsApi,
            productCategoryDao,
            networkUtils
        )

    @Provides
    @Singleton
    fun provideProductsRepository(
        dispatcherProvider: DispatcherProvider,
        productsApi: ProductsApi,
        productDao: ProductDao,
        networkUtils: NetworkUtils
    ): ProductsRepository =
        ProductsRepositoryImpl(dispatcherProvider, productsApi, productDao, networkUtils)

    @Provides
    @Singleton
    fun provideSalesRepository(
        dispatcherProvider: DispatcherProvider,
        salesApi: SalesApi,
        salesDao: SalesDao,
        networkUtils: NetworkUtils,
        workManager: WorkManager
    ): SalesRepository =
        SalesRepositoryImpl(dispatcherProvider, networkUtils, salesApi, salesDao, workManager)

    @Singleton
    @Provides
    fun provideWorkManager(@ApplicationContext context: Context): WorkManager =
        WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideCartRepository(
        dispatcherProvider: DispatcherProvider,
        cartDao: CartDao
    ): CartRepository = CartRepositoryImpl(cartDao, dispatcherProvider)
}
