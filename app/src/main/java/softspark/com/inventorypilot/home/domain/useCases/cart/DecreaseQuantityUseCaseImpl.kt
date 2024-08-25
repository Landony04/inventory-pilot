package softspark.com.inventorypilot.home.domain.useCases.cart

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import javax.inject.Inject

class DecreaseQuantityUseCaseImpl @Inject constructor(private val cartRepository: CartRepository) :
    DecreaseQuantityUseCase {
    override suspend fun invoke(cartItemId: String): Flow<Result<Unit>> =
        cartRepository.decreaseQuantity(cartItemId)
}