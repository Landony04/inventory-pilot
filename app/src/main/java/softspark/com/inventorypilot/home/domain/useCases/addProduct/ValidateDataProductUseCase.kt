package softspark.com.inventorypilot.home.domain.useCases.addProduct

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.home.domain.entities.AddProductResult

interface ValidateDataProductUseCase {
    operator fun invoke(
        categoryId: String,
        name: String,
        description: String,
        stock: String,
        price: String,
        priceCost: String
    ): Flow<AddProductResult>
}