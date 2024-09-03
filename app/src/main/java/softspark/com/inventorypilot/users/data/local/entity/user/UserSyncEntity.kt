package softspark.com.inventorypilot.users.data.local.entity.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UserSyncEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String
)
