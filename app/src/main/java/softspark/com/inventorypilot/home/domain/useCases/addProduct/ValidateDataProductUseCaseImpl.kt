package softspark.com.inventorypilot.home.domain.useCases.addProduct

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import softspark.com.inventorypilot.R
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
        price: String,
        priceCost: String
    ): Flow<AddProductResult> {
        return flow {
            if (stock.toIntOrNull() == null) {
                return@flow emit(AddProductResult.Invalid(context.getString(R.string.text_error_stock_invalid)))
            }

            if (price.toDoubleOrNull() == null) {
                return@flow emit(AddProductResult.Invalid(context.getString(R.string.text_error_price_invalid)))
            }

            if (name.isEmpty()) {
                return@flow emit(AddProductResult.Invalid(context.getString(R.string.text_error_without_name)))
            }

            if (description.isEmpty()) {
                return@flow emit(AddProductResult.Invalid(context.getString(R.string.text_error_without_description)))
            }

            if (stock.isEmpty()) {
                return@flow emit(AddProductResult.Invalid(context.getString(R.string.text_error_without_stock)))
            }

            if (price.isEmpty()) {
                return@flow emit(AddProductResult.Invalid(context.getString(R.string.text_error_without_price)))
            }

            if (categoryId.isEmpty()) {
                return@flow emit(AddProductResult.Invalid(context.getString(R.string.text_error_without_category)))
            }

            if (priceCost.toDoubleOrNull() == null) {
                return@flow emit(AddProductResult.Invalid(context.getString(R.string.text_error_price_cost_invalid)))
            }

            return@flow emit(AddProductResult.Valid)
        }
    }
}