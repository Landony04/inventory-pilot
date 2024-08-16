package softspark.com.inventorypilot.home.data.local.entity.products

import androidx.room.Entity

@Entity
data class ProductEntity(
    val id: String,
    val categoryId: String,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int
)
