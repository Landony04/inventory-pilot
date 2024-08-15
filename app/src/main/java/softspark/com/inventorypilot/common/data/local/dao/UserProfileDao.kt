package softspark.com.inventorypilot.common.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfileEntity: UserProfileEntity)

    @Query("SELECT * FROM UserProfileEntity WHERE id = :id")
    fun getUserProfileById(id: String): UserProfileEntity
}
