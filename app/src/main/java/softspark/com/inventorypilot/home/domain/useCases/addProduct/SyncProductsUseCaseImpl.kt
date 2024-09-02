package softspark.com.inventorypilot.home.domain.useCases.addProduct

import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import javax.inject.Inject

class SyncProductsUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : SyncProductsUseCase {
    override suspend fun invoke() = productsRepository.syncProducts()
}