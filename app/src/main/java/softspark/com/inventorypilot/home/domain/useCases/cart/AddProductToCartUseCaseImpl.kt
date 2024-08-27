package softspark.com.inventorypilot.home.domain.useCases.cart

import softspark.com.inventorypilot.home.data.repositories.CartRepository
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import javax.inject.Inject

class AddProductToCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : AddProductToCartUseCase {
    override suspend fun invoke(cartItem: CartItem) = cartRepository.addToCart(cartItem)
}