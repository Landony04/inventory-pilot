package softspark.com.inventorypilot.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import softspark.com.inventorypilot.home.domain.useCases.cart.DecreaseQuantityUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.DecreaseQuantityUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.IncreaseQuantityUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.IncreaseQuantityUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.AddProductToCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.AddProductToCartUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.EmptyCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.EmptyCartUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.GetCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.GetCartUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.DecreaseStockUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.DecreaseStockUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByCategoryIdUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByCategoryIdUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByNameUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByNameUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.IncreaseStockUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.IncreaseStockUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesByDateUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesByDateUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object HomeUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetProductCategoriesUseCase(
        productCategoriesRepository: ProductCategoriesRepository
    ): GetProductCategoriesUseCase = GetProductCategoriesUseCaseImpl(productCategoriesRepository)

    @ViewModelScoped
    @Provides
    fun provideGetProductsByCategoryId(
        productsRepository: ProductsRepository
    ): GetProductsByCategoryIdUseCase = GetProductsByCategoryIdUseCaseImpl(productsRepository)

    //USE CASES FOR PRODUCTS

    @ViewModelScoped
    @Provides
    fun provideGetProductsUseCase(
        productsRepository: ProductsRepository
    ): GetProductsUseCase = GetProductsUseCaseImpl(productsRepository)

    @ViewModelScoped
    @Provides
    fun provideGetProductsByName(
        productsRepository: ProductsRepository
    ): GetProductsByNameUseCase = GetProductsByNameUseCaseImpl(productsRepository)

    @ViewModelScoped
    @Provides
    fun provideDecreaseStockUseCase(
        productsRepository: ProductsRepository
    ): DecreaseStockUseCase = DecreaseStockUseCaseImpl(productsRepository)

    @ViewModelScoped
    @Provides
    fun provideIncreaseStockUseCase(
        productsRepository: ProductsRepository
    ): IncreaseStockUseCase = IncreaseStockUseCaseImpl(productsRepository)

    //USE CASES FOR SALES

    @ViewModelScoped
    @Provides
    fun provideGetSalesUseCase(
        salesRepository: SalesRepository
    ): GetSalesUseCase = GetSalesUseCaseImpl(salesRepository)

    @ViewModelScoped
    @Provides
    fun provideGetSalesByDateUseCase(
        salesRepository: SalesRepository
    ): GetSalesByDateUseCase = GetSalesByDateUseCaseImpl(salesRepository)

    //USE CASES FOR CART
    @ViewModelScoped
    @Provides
    fun provideAddProductToCartUseCase(
        cartRepository: CartRepository
    ): AddProductToCartUseCase = AddProductToCartUseCaseImpl(cartRepository)

    @ViewModelScoped
    @Provides
    fun provideEmptyCartUseCase(
        cartRepository: CartRepository
    ): EmptyCartUseCase = EmptyCartUseCaseImpl(cartRepository)

    @ViewModelScoped
    @Provides
    fun provideGetCartUseCase(
        cartRepository: CartRepository
    ): GetCartUseCase = GetCartUseCaseImpl(cartRepository)

    @ViewModelScoped
    @Provides
    fun provideDecreaseStockUseCase(
        cartRepository: CartRepository
    ): DecreaseQuantityUseCase = DecreaseQuantityUseCaseImpl(cartRepository)

    @ViewModelScoped
    @Provides
    fun provideIncreaseStockUseCase(
        cartRepository: CartRepository
    ): IncreaseQuantityUseCase = IncreaseQuantityUseCaseImpl(cartRepository)
}
