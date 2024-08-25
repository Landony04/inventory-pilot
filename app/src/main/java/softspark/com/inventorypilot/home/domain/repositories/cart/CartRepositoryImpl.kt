package softspark.com.inventorypilot.home.domain.repositories.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.local.dao.cart.CartDao
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val dispatchers: DispatcherProvider
) : CartRepository {

    override suspend fun addToCart(cartItem: CartItem): Flow<Result<Boolean>> =
        flow<Result<Boolean>> {

        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun getCart(): Flow<Result<ArrayList<CartItem>>> =
        flow<Result<ArrayList<CartItem>>> {

        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun emptyCart(): Flow<Result<Boolean>> =
        flow<Result<Boolean>> {

        }.onStart {
            emit(Result.Loading)
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())
}