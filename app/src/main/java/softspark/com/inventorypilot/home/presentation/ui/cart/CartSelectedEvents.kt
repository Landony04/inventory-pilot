package softspark.com.inventorypilot.home.presentation.ui.cart

import softspark.com.inventorypilot.home.domain.models.cart.CartSelectedType

interface CartSelectedEvents {
    fun updateQuantity(cartSelectedType: CartSelectedType)
}