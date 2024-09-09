package softspark.com.inventorypilot.home.data.local.entity.products

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "productId")
    val productId: String,
    val categoryId: String,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    var addDate: String,
    var createBy: String
)
