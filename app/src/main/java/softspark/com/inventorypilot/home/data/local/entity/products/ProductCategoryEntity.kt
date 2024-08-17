package softspark.com.inventorypilot.home.data.local.entity.products

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ProductCategoryEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "categoryId")
    val categoryId: String,
    val name: String
)
