package softspark.com.inventorypilot.home.data.local.dao.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.home.data.local.entity.cart.CartItemEntity

@Dao
interface CartDao {
    @Insert
    suspend fun addToCart(cartItem: CartItemEntity)

    @Query("SELECT * FROM CartItemEntity ORDER BY productId ASC")
    fun getCart(): Flow<List<CartItemEntity>>

    @Query("SELECT * FROM CartItemEntity WHERE productId = :productId")
    fun getCartByProductId(productId: String): CartItemEntity?

    @Query("DELETE FROM CartItemEntity")
    suspend fun emptyCart()

    @Query("UPDATE CartItemEntity SET quantity = quantity + :quantity WHERE cartItemId = :cartItemId")
    suspend fun increaseQuantity(cartItemId: String, quantity: Int)

    @Query("UPDATE CartItemEntity SET quantity = quantity - :quantity WHERE cartItemId = :cartItemId")
    suspend fun decreaseQuantity(cartItemId: String, quantity: Int)

    @Query("UPDATE CartItemEntity SET totalAmount = quantity * price WHERE cartItemId = :cartItemId")
    suspend fun updateTotalAmount(cartItemId: String)

    @Query("DELETE FROM CartItemEntity WHERE cartItemId = :cartItemId")
    suspend fun deleteCartItem(cartItemId: String)
}