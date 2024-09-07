package softspark.com.inventorypilot.home.remote.dto.products

data class UpdateProductDto(
    val categoryId: String? = null,
    val name: String? = null,
    val description: String? = null,
    val price: Double? = null,
    val stock: Int? = null,
    val addDate: String? = null,
    val createBy: String? = null
)
