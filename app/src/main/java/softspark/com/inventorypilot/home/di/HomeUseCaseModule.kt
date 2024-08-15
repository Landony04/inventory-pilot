package softspark.com.inventorypilot.home.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCaseImpl

@Module
@InstallIn(ViewModelComponent::class)
object HomeUseCaseModule {

    @ViewModelScoped
    @Provides
    fun provideGetProductCategoriesUseCase(
        productCategoriesRepository: ProductCategoriesRepository
    ): GetProductCategoriesUseCase = GetProductCategoriesUseCaseImpl(productCategoriesRepository)
}
