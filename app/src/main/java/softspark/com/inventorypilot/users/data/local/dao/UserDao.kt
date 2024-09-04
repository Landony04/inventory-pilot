package softspark.com.inventorypilot.users.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import softspark.com.inventorypilot.users.data.local.entity.user.UserSyncEntity

@Dao
interface UserDao {

    @Query("SELECT * FROM UserSyncEntity")
    fun getAllUserSync(): List<UserSyncEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserSync(userSyncEntity: UserSyncEntity)

    @Delete
    suspend fun deleteUserSync(userSyncEntity: UserSyncEntity)

}
