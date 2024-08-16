package softspark.com.inventorypilot.home.data.local.dao.sales

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleEntity

@Dao
interface SalesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSale(saleEntity: SaleEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSales(saleEntity: List<SaleEntity>)

    @Query("SELECT * FROM SaleEntity WHERE id = :id")
    fun getSaleById(id: String): SaleEntity

    @Query("SELECT * FROM SaleEntity")
    fun getAllSales(): List<SaleEntity>
}
