package softspark.com.inventorypilot.home.domain.useCases.cart

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.sales.CartItem

interface AddProductToCartUseCase {
    suspend operator fun invoke(cartItem: CartItem)
}