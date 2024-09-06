package softspark.com.inventorypilot.common.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity

@Dao
interface UserProfileDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(userProfileEntity: List<UserProfileEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userProfileEntity: UserProfileEntity)

    @Query("SELECT * FROM UserProfileEntity")
    fun getAllUsers(): List<UserProfileEntity>

    @Query("SELECT * FROM UserProfileEntity WHERE userId = :id")
    fun getUserProfileById(id: String): UserProfileEntity

    @Query("SELECT * FROM UserProfileEntity WHERE email = :email")
    fun getUserProfileByEmail(email: String): UserProfileEntity
}
