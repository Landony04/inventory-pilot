package softspark.com.inventorypilot.home.data.local.dao.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import softspark.com.inventorypilot.home.data.local.entity.cart.CartItemEntity

@Dao
interface CartDao {
    @Insert
    suspend fun addToCart(cartItem: CartItemEntity)

    @Query("SELECT * FROM CartItemEntity")
    suspend fun getCart(): List<CartItemEntity>

    @Query("DELETE FROM CartItemEntity")
    suspend fun emptyCart()
}