package softspark.com.inventorypilot.home.data.local.entity.sales

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SaleSyncEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String
)
