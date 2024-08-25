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

    @Query("UPDATE CartItemEntity SET quantity = quantity + :quantity WHERE cartItemId = :cartItemId")
    suspend fun increaseQuantity(cartItemId: String, quantity: Int)

    @Query("UPDATE CartItemEntity SET quantity = quantity - :quantity WHERE cartItemId = :cartItemId")
    suspend fun decreaseQuantity(cartItemId: String, quantity: Int)
}