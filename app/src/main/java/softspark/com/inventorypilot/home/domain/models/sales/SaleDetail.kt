package softspark.com.inventorypilot.home.domain.models.sales

data class SaleDetail(
    val statusWithFormat: String,
    val dateWithFormat: String,
    val userNameWithFormat: String,
    val totalAmount: String,
    val products: List<ProductSale>
)
