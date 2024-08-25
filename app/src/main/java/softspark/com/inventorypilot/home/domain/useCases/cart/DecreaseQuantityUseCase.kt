package softspark.com.inventorypilot.home.domain.useCases.cart

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result

interface DecreaseQuantityUseCase {
    suspend operator fun invoke(cartItemId: String): Flow<Result<Unit>>
}