package softspark.com.inventorypilot.home.data.local.dao.sales

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleEntity

@Dao
interface SalesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(saleEntity: SaleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSales(saleEntity: List<SaleEntity>)

    @Transaction
    @Query("SELECT * FROM SaleEntity WHERE saleId = :id")
    fun getSaleById(id: String): SaleEntity

    @Transaction
    @Query("SELECT * FROM SaleEntity")
    fun getAllSales(): List<SaleEntity>
}
