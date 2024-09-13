package softspark.com.inventorypilot.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.local.dao.sales.SalesDao
import softspark.com.inventorypilot.home.data.sync.CombinedWorkerFactory
import softspark.com.inventorypilot.home.data.sync.factories.CustomWorkerFactory
import softspark.com.inventorypilot.home.data.sync.factories.CustomWorkerFactoryCategoryProduct
import softspark.com.inventorypilot.home.data.sync.factories.CustomWorkerFactoryProduct
import softspark.com.inventorypilot.home.remote.ProductsApi
import softspark.com.inventorypilot.home.remote.SalesApi
import softspark.com.inventorypilot.users.data.sync.CustomWorkerFactoryUser
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeWorkerModule {

    @Provides
    @Singleton
    fun provideCategoryProductWorkerFactory(
        productsApi: ProductsApi,
        productCategoryDao: ProductCategoryDao
    ): CustomWorkerFactoryCategoryProduct = CustomWorkerFactoryCategoryProduct(
        productsApi, productCategoryDao
    )

    @Provides
    @Singleton
    fun provideWorkerFactorySales(
        salesApi: SalesApi,
        salesDao: SalesDao,
        productDao: ProductDao
    ): CustomWorkerFactory = CustomWorkerFactory(
        salesApi, salesDao, productDao
    )

    @Provides
    @Singleton
    fun provideWorkerFactoryProduct(
        productsApi: ProductsApi,
        productDao: ProductDao
    ): CustomWorkerFactoryProduct = CustomWorkerFactoryProduct(
        productsApi, productDao
    )

    @Provides
    @Singleton
    fun provideCombinedWorkerFactory(
        customWorkerFactory: CustomWorkerFactory,
        customWorkerFactoryProduct: CustomWorkerFactoryProduct,
        customWorkerFactoryUser: CustomWorkerFactoryUser,
        customWorkerFactoryCategoryProduct: CustomWorkerFactoryCategoryProduct
    ): CombinedWorkerFactory = CombinedWorkerFactory(
        customWorkerFactory,
        customWorkerFactoryProduct,
        customWorkerFactoryUser,
        customWorkerFactoryCategoryProduct
    )
}

