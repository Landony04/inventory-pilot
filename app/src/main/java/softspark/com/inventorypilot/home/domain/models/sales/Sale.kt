package softspark.com.inventorypilot.home.domain.models.sales

data class Sale(
    val id: String,
    val clientId: String,
    val date: String,
    val totalAmount: Double,
    val userId: String,
    val products: ArrayList<ProductSale>
)