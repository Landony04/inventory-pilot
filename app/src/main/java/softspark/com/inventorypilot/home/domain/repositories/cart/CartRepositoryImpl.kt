package softspark.com.inventorypilot.home.domain.repositories.cart

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.REQUEST_DELAY
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

    override suspend fun addToCart(cartItem: CartItem): Flow<Result<Boolean>> =
        flow<Result<Boolean>> {
            cartDao.addToCart(cartItem.toCartItemEntity())
            delay(REQUEST_DELAY)
            emit(Result.Success(data = true))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun getCart(): Flow<Result<ArrayList<CartItem>>> =
        flow<Result<ArrayList<CartItem>>> {
            val cart = cartDao.getCart().map { it.toCartItem() }
            delay(REQUEST_DELAY)
            emit(Result.Success(data = ArrayList(cart)))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun emptyCart(): Flow<Result<Boolean>> =
        flow<Result<Boolean>> {
            cartDao.emptyCart()
            delay(REQUEST_DELAY)
            emit(Result.Success(data = true))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun increaseQuantity(cartItemId: String): Flow<Result<Unit>> =
        flow<Result<Unit>> {
            cartDao.increaseQuantity(cartItemId, 1)
            cartDao.updateTotalAmount(cartItemId)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun decreaseQuantity(cartItemId: String): Flow<Result<Unit>> =
        flow<Result<Unit>> {
            cartDao.decreaseQuantity(cartItemId, 1)
            cartDao.updateTotalAmount(cartItemId)
            emit(Result.Success(Unit))
        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())
}