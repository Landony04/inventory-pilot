package softspark.com.inventorypilot.home.domain.repositories.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.local.dao.cart.CartDao
import softspark.com.inventorypilot.home.data.mapper.cart.toCartItem
import softspark.com.inventorypilot.home.data.mapper.cart.toCartItemEntity
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val dispatchers: DispatcherProvider
) : CartRepository {

    override suspend fun addToCart(cartItem: CartItem) = withContext(dispatchers.io()) {
        cartDao.addToCart(cartItem.toCartItemEntity())
    }

    override suspend fun getCart(): Flow<Result<ArrayList<CartItem>>> =
        flow<Result<ArrayList<CartItem>>> {
            cartDao.getCart().collect { listCart ->
                val result = listCart.map { it.toCartItem() }
                emit(Result.Success(data = ArrayList(result)))
            }
        }.onStart {
            emit(Result.Success(data = arrayListOf()))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io()).distinctUntilChanged()

    override suspend fun emptyCart(): Flow<Result<Boolean>> =
        flow<Result<Boolean>> {
            cartDao.emptyCart()
            emit(Result.Success(data = true))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io()).distinctUntilChanged()

    override suspend fun increaseQuantity(cartItemId: String) = withContext(dispatchers.io()) {
        cartDao.increaseQuantity(cartItemId, 1)
        cartDao.updateTotalAmount(cartItemId)
    }

    override suspend fun decreaseQuantity(cartItemId: String) = withContext(dispatchers.io()) {
        cartDao.decreaseQuantity(cartItemId, 1)
        cartDao.updateTotalAmount(cartItemId)
    }

    override suspend fun deleteCartItem(cartItemId: String) = withContext(dispatchers.io()) {
        cartDao.deleteCartItem(cartItemId)
    }
}
