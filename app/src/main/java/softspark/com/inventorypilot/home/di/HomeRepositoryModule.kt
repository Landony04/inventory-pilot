package softspark.com.inventorypilot.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.repositories.products.ProductCategoriesRepositoryImpl
import softspark.com.inventorypilot.home.domain.repositories.products.ProductsRepositoryImpl
import softspark.com.inventorypilot.home.remote.ProductsApi
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeRepositoryModule {
    @Provides
    @Singleton
    fun provideProductCategoriesRepository(
        dispatcherProvider: DispatcherProvider,
        productsApi: ProductsApi,
        productCategoryDao: ProductCategoryDao
    ): ProductCategoriesRepository =
        ProductCategoriesRepositoryImpl(dispatcherProvider, productsApi, productCategoryDao)

    @Provides
    @Singleton
    fun provideProductsRepository(
        dispatcherProvider: DispatcherProvider,
        productsApi: ProductsApi,
        productDao: ProductDao
    ): ProductsRepository = ProductsRepositoryImpl(dispatcherProvider, productsApi, productDao)
}
