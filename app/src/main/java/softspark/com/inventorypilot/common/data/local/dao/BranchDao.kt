package softspark.com.inventorypilot.common.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import softspark.com.inventorypilot.common.data.local.entity.BranchEntity

@Dao
interface BranchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBranch(branches: List<BranchEntity>)

    @Query("SELECT * FROM BranchEntity")
    fun getAllBranches(): List<BranchEntity>
}
