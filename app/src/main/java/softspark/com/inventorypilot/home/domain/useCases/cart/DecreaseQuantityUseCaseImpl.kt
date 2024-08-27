package softspark.com.inventorypilot.home.domain.useCases.cart

import softspark.com.inventorypilot.home.data.repositories.CartRepository
import javax.inject.Inject

class DecreaseQuantityUseCaseImpl @Inject constructor(private val cartRepository: CartRepository) :
    DecreaseQuantityUseCase {
    override suspend fun invoke(cartItemId: String) = cartRepository.decreaseQuantity(cartItemId)
}