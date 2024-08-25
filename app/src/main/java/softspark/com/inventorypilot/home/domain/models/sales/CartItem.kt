package softspark.com.inventorypilot.home.domain.models.sales

data class CartItem(
    val productId: String,
    val quantity: Int,
    val price: Double
)