package softspark.com.inventorypilot.home.domain.models.sales

import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.login.domain.models.UserProfile

data class Sale(
    val id: String,
    val clientId: String,
    val date: String,
    val totalAmount: Double,
    val user: UserProfile,
    val products: ArrayList<Product>
)
