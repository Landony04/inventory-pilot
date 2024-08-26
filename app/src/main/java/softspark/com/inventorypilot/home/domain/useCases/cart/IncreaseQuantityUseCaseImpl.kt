package softspark.com.inventorypilot.home.domain.useCases.cart

import softspark.com.inventorypilot.home.data.repositories.CartRepository
import javax.inject.Inject

class IncreaseQuantityUseCaseImpl @Inject constructor(private val cartRepository: CartRepository) :
    IncreaseQuantityUseCase {
    override suspend fun invoke(cartItemId: String) =
        cartRepository.increaseQuantity(cartItemId = cartItemId)
}