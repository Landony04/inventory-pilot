package softspark.com.inventorypilot.home.data.local.entity.sales

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity
import softspark.com.inventorypilot.home.data.local.entity.products.SaleProductsList

@Entity(
    foreignKeys = [ForeignKey(
        entity = UserProfileEntity::class,
        parentColumns = ["userId"],
        childColumns = ["userOwnerId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class SaleEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "saleId")
    val saleId: String,
    val clientId: String,
    val date: String,
    val dateWithoutHours: String,
    val totalAmount: Double,
    @ColumnInfo(name = "userOwnerId")
    // ID del usuario que realizó la venta (ForeignKey)
    val userOwnerId: String,
    val products: SaleProductsList
)
