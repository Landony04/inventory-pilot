package softspark.com.inventorypilot.home.domain.useCases.products

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.models.products.Product
import javax.inject.Inject

class GetProductsByNameUseCaseImpl @Inject constructor(
    private val productsRepository: ProductsRepository
) : GetProductsByNameUseCase {
    override suspend fun invoke(query: String): Flow<Result<ArrayList<Product>>> =
        productsRepository.getProductsByName(query).distinctUntilChanged()
}