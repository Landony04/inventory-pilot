package softspark.com.inventorypilot.home.presentation.ui.products.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.DELAY_TIME
import softspark.com.inventorypilot.home.domain.entities.AddProductResult
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.domain.useCases.addProduct.AddProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.addProduct.ValidateDataProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCase
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase,
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase,
    private val validateDataProductUseCase: ValidateDataProductUseCase
) : ViewModel() {

    private val _productCategoryData = MutableLiveData<Result<ArrayList<ProductCategory>>>()
    val productCategoryData: LiveData<Result<ArrayList<ProductCategory>>> get() = _productCategoryData

    private val _validateProductData = MutableLiveData<AddProductResult>()
    val validateProductData: LiveData<AddProductResult> get() = _validateProductData

    fun addProduct(
        categoryId: String,
        name: String,
        description: String,
        stock: String,
        price: String
    ) {
        viewModelScope.launch {
            delay(DELAY_TIME)
            validateDataProductUseCase(
                categoryId = categoryId,
                name = name,
                description = description,
                stock = stock,
                price = price
            ).collect { productResult ->
                when (productResult) {
                    is AddProductResult.Invalid -> _validateProductData.value = productResult
                    AddProductResult.Valid -> {
                        val product = Product(
                            id = UUID.randomUUID().toString(),
                            categoryId = categoryId,
                            name = name,
                            description = description,
                            price = price.toDouble(),
                            stock = stock.toInt()
                        )
                        _validateProductData.value = productResult
                        addProductUseCase(product)
                    }
                }
            }
        }
    }

    fun getProductCategories() {
        viewModelScope.launch {
            getProductCategoriesUseCase().collect { result ->
                _productCategoryData.value = result
            }
        }
    }
}