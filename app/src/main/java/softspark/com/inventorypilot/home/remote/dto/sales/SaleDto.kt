package softspark.com.inventorypilot.home.remote.dto.sales

data class SaleDto(
    val clientId: String,
    val date: String,
    val totalAmount: Double,
    val userId: String,
    val products: ProductsSaleResponse
)