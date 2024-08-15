package softspark.com.inventorypilot.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import softspark.com.inventorypilot.common.data.local.dao.UserProfileDao
import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity

@Database(entities = [UserProfileEntity::class], version = 1)
abstract class InventoryPilotDatabase(): RoomDatabase() {
    abstract val dao: UserProfileDao
}