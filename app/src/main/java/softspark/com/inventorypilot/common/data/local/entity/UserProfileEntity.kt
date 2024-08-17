package softspark.com.inventorypilot.common.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserProfileEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "userId")
    val userId: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String
)
