package softspark.com.inventorypilot.home.domain.models.sales

data class CartItem(
    val cartItemId: Int? = null,
    val productId: String,
    var quantity: Int,
    val price: Double,
    var totalAmount: Double? = null,
    val productName: String
)
