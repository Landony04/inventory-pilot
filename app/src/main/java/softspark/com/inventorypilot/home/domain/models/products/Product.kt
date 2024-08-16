package softspark.com.inventorypilot.home.domain.models.products

data class Product(
    val id: String,
    val categoryId: String,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int
)
