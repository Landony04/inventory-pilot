package softspark.com.inventorypilot.home.remote.dto.products

data class ProductDto(
    val categoryId: String,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int
)