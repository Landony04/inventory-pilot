package softspark.com.inventorypilot.home.domain.models.sales

data class ProductSale(
    val id: String,
    val price: Double,
    val quantity: Int,
    var name: String? = null
)
