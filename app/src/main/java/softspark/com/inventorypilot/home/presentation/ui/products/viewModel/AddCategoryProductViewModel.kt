package softspark.com.inventorypilot.home.presentation.ui.products.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.domain.useCases.GenerateIdUseCase
import softspark.com.inventorypilot.home.domain.entities.AddCategoryProductResult
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct.AddCategoryProductUseCase
import javax.inject.Inject

@HiltViewModel
class AddCategoryProductViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val generateIdUseCase: GenerateIdUseCase,
    private val addCategoryProductUseCase: AddCategoryProductUseCase
) : ViewModel() {

    private val _categoryData = MutableLiveData<AddCategoryProductResult>()
    val categoryData: LiveData<AddCategoryProductResult> get() = _categoryData

    fun addCategory(name: String) {
        viewModelScope.launch {
            if (name.isNotEmpty()) {
                addCategoryProductUseCase(
                    ProductCategory(
                        id = generateIdUseCase(),
                        name = name
                    )
                )

                _categoryData.value = AddCategoryProductResult.Valid
            } else {
                _categoryData.value =
                    AddCategoryProductResult.Invalid(context.getString(R.string.text_name_empty_error))
            }
        }
    }
}
