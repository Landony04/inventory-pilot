package softspark.com.inventorypilot.home.data.repositories

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.sales.CartItem

interface CartRepository {
    suspend fun addToCart(cartItem: CartItem): Flow<Result<Boolean>>

    suspend fun getCart(): Flow<Result<ArrayList<CartItem>>>

    suspend fun emptyCart(): Flow<Result<Boolean>>

    suspend fun increaseQuantity(cartItemId: String): Flow<Result<Unit>>

    suspend fun decreaseQuantity(cartItemId: String): Flow<Result<Unit>>
}