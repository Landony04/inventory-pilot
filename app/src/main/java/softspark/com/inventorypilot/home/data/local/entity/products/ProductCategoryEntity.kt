package softspark.com.inventorypilot.home.data.local.entity.products

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product_category")
data class ProductCategoryEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String
)
