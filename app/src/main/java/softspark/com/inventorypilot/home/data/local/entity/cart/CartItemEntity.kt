package softspark.com.inventorypilot.home.data.local.entity.cart

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CartItemEntity(
    @PrimaryKey(autoGenerate = true)
    val cartItemId: Int?,
    val productId: String,
    val quantity: Int,
    val price: Double,
    val priceCost: Double,
    val totalAmount: Double,
    val productName: String
)
