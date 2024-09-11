package softspark.com.inventorypilot.home.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.data.repositories.SalesRepository
import softspark.com.inventorypilot.home.data.repositories.SessionRepository
import softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct.AddCategoryProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct.AddCategoryProductUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct.SyncCategoryProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct.SyncCategoryProductUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.addProduct.AddProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.addProduct.AddProductUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.addProduct.SyncProductsUseCase
import softspark.com.inventorypilot.home.domain.useCases.addProduct.SyncProductsUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.addProduct.ValidateDataProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.addProduct.ValidateDataProductUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.AddProductToCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.AddProductToCartUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.DecreaseQuantityUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.DecreaseQuantityUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.DeleteCartItemUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.DeleteCartItemUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.EmptyCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.EmptyCartUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.GetCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.GetCartUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.cart.IncreaseQuantityUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.IncreaseQuantityUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductByIdUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductByIdUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByCategoryIdUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByCategoryIdUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByNameUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByNameUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.products.UpdateProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.UpdateProductUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesByDateUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesByDateUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.sales.InsertSaleUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.InsertSaleUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.sales.SyncSalesUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.SyncSalesUseCaseImpl
import softspark.com.inventorypilot.home.domain.useCases.session.LogoutUseCase
import softspark.com.inventorypilot.home.domain.useCases.session.LogoutUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object HomeUseCaseModule {

    //USE CASES FOR CATEGORIES PRODUCTS
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

    @ViewModelScoped
    @Provides
    fun provideAddCategoryProduct(
        productCategoriesRepository: ProductCategoriesRepository
    ): AddCategoryProductUseCase = AddCategoryProductUseCaseImpl(productCategoriesRepository)

    @ViewModelScoped
    @Provides
    fun provideSyncCategoryProduct(
        productCategoriesRepository: ProductCategoriesRepository
    ): SyncCategoryProductUseCase = SyncCategoryProductUseCaseImpl(productCategoriesRepository)

    //USE CASES FOR PRODUCTS
    @ViewModelScoped
    @Provides
    fun provideAddProductUseCase(
        productsRepository: ProductsRepository
    ): AddProductUseCase = AddProductUseCaseImpl(productsRepository)

    @ViewModelScoped
    @Provides
    fun provideValidateAddProductUseCase(
        @ApplicationContext context: Context
    ): ValidateDataProductUseCase = ValidateDataProductUseCaseImpl(context)

    @ViewModelScoped
    @Provides
    fun provideSyncProductUseCase(
        productsRepository: ProductsRepository
    ): SyncProductsUseCase = SyncProductsUseCaseImpl(productsRepository)

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
    fun provideGetProductsById(
        productsRepository: ProductsRepository
    ): GetProductByIdUseCase = GetProductByIdUseCaseImpl(productsRepository)

    @ViewModelScoped
    @Provides
    fun provideUpdateProduct(
        productsRepository: ProductsRepository
    ): UpdateProductUseCase = UpdateProductUseCaseImpl(productsRepository)

    //USE CASES FOR SALES
    @ViewModelScoped
    @Provides
    fun provideGetSalesByDateUseCase(
        salesRepository: SalesRepository
    ): GetSalesByDateUseCase = GetSalesByDateUseCaseImpl(salesRepository)

    @ViewModelScoped
    @Provides
    fun provideSyncSalesUseCase(
        salesRepository: SalesRepository
    ): SyncSalesUseCase = SyncSalesUseCaseImpl(salesRepository)

    @ViewModelScoped
    @Provides
    fun provideInsertSaleUseCase(
        salesRepository: SalesRepository
    ): InsertSaleUseCase = InsertSaleUseCaseImpl(salesRepository)

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

    @ViewModelScoped
    @Provides
    fun provideDeleteCartItemUseCase(
        cartRepository: CartRepository
    ): DeleteCartItemUseCase = DeleteCartItemUseCaseImpl(cartRepository)

    //USE CASES FOR SESSION
    @ViewModelScoped
    @Provides
    fun provideLogoutUseCase(
        sessionRepository: SessionRepository
    ): LogoutUseCase = LogoutUseCaseImpl(sessionRepository)
}
