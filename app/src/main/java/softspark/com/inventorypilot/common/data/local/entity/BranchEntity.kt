package softspark.com.inventorypilot.common.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BranchEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "branchId")
    val id: String,
    val name: String,
    val address: String
)
