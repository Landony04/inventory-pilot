package softspark.com.inventorypilot.home.remote.dto.sales

data class SaleDto(
    val clientId: String? = null,
    val date: String,
    val totalAmount: Double,
    val userId: String,
    val products: ProductsSaleResponse,
    val status: String
)