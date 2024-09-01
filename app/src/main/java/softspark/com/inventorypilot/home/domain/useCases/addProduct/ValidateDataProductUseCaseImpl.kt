package softspark.com.inventorypilot.home.domain.useCases.addProduct

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import softspark.com.inventorypilot.home.domain.entities.AddProductResult
import javax.inject.Inject

class ValidateDataProductUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ValidateDataProductUseCase {
    override fun invoke(
        categoryId: String,
        name: String,
        description: String,
        stock: String,
        price: String
    ): Flow<AddProductResult> {
        return flow {

            if (stock.toIntOrNull() == null) {
                return@flow emit(AddProductResult.Invalid("El stock ingresado no es un número válido."))
            }

            if (price.toDoubleOrNull() == null) {
                return@flow emit(AddProductResult.Invalid("El precio ingresado no es un número válido."))
            }

            if (name.isEmpty()) {
                return@flow emit(AddProductResult.Invalid("Colocale nombre a tu producto"))
            }

            if (description.isEmpty()) {
                return@flow emit(AddProductResult.Invalid("Colocale una descripción a tu producto"))
            }

            if (stock.toString().isEmpty()) {
                return@flow emit(AddProductResult.Invalid("Coloca la cantidad ingresada a tu producto"))
            }

            if (price.toString().isEmpty()) {
                return@flow emit(AddProductResult.Invalid("Colocale precio a tu producto"))
            }

            if (categoryId.isEmpty()) {
                return@flow emit(AddProductResult.Invalid("Selecciona una categoría para tu producto"))
            }
        }
    }
}