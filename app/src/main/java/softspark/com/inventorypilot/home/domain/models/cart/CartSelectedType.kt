package softspark.com.inventorypilot.home.domain.models.cart

sealed class CartSelectedType {
    data class DecreaseQuantity(val itemCartId: String) : CartSelectedType()

    data class IncreaseQuantity(val itemCartId: String) : CartSelectedType()

    data class RemoveCartItem(val itemCartId: String) : CartSelectedType()
}