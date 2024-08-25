package softspark.com.inventorypilot.home.domain.useCases.cart

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import javax.inject.Inject

class GetCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
): GetCartUseCase {
    override suspend fun invoke(): Flow<Result<ArrayList<CartItem>>> = cartRepository.getCart()
}