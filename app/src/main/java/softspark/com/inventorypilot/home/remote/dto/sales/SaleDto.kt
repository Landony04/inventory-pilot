package softspark.com.inventorypilot.home.remote.dto.sales

import softspark.com.inventorypilot.home.remote.dto.products.GetProductsResponse
import softspark.com.inventorypilot.home.remote.dto.products.ProductDto

data class SaleDto(
    val clientId: String,
    val date: String,
    val totalAmount: Double,
    val user: UserProfileResponse,
    val products: GetProductsResponse
)