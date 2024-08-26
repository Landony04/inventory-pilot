package softspark.com.inventorypilot.home.domain.useCases.cart

import softspark.com.inventorypilot.home.data.repositories.CartRepository
import javax.inject.Inject

class DeleteCartItemUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : DeleteCartItemUseCase {
    override suspend fun invoke(cartItemId: String) = cartRepository.deleteCartItem(cartItemId)
}