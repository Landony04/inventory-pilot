package softspark.com.inventorypilot.home.domain.useCases.cart

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result

interface EmptyCartUseCase {
    suspend operator fun invoke(): Flow<Result<Boolean>>
}