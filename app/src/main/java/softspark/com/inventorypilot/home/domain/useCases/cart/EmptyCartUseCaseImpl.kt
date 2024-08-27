package softspark.com.inventorypilot.home.domain.useCases.cart

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.CartRepository
import javax.inject.Inject

class EmptyCartUseCaseImpl @Inject constructor(
    private val cartRepository: CartRepository
) : EmptyCartUseCase {
    override suspend fun invoke(): Flow<Result<Boolean>> =
        cartRepository.emptyCart().distinctUntilChanged()
}