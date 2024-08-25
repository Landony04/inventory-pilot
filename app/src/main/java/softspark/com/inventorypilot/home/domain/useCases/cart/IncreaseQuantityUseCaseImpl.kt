package softspark.com.inventorypilot.home.domain.useCases.cart

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import javax.inject.Inject

class IncreaseQuantityUseCaseImpl @Inject constructor(private val cartRepository: CartRepository) :
    IncreaseQuantityUseCase {
    override suspend fun invoke(cartItemId: String): Flow<Result<Unit>> =
        cartRepository.increaseQuantity(cartItemId = cartItemId)
}