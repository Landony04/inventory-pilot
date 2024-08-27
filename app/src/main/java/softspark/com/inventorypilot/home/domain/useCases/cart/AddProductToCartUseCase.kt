package softspark.com.inventorypilot.home.domain.useCases.cart

import softspark.com.inventorypilot.home.domain.models.sales.CartItem

interface AddProductToCartUseCase {
    suspend operator fun invoke(cartItem: CartItem)
}