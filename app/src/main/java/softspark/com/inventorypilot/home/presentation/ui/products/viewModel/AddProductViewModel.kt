package softspark.com.inventorypilot.home.presentation.ui.products.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.domain.useCases.GenerateCurrentDateUTCUseCase
import softspark.com.inventorypilot.common.domain.useCases.GenerateIdUseCase
import softspark.com.inventorypilot.common.domain.useCases.GetUserIdUseCase
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.DELAY_TIME
import softspark.com.inventorypilot.common.utils.Constants.EMPTY_STRING
import softspark.com.inventorypilot.home.domain.entities.AddProductResult
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.domain.useCases.addProduct.AddProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.addProduct.ValidateDataProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductByIdUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.UpdateProductUseCase
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(
    private val addProductUseCase: AddProductUseCase,
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase,
    private val validateDataProductUseCase: ValidateDataProductUseCase,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val getUserIdUseCase: GetUserIdUseCase,
    private val getDateUTCUseCase: GenerateCurrentDateUTCUseCase,
    private val updateProductUseCase: UpdateProductUseCase,
    private val generateIdUseCase: GenerateIdUseCase
) : ViewModel() {

    private val _productCategoryData = MutableLiveData<Result<ArrayList<ProductCategory>>>()
    val productCategoryData: LiveData<Result<ArrayList<ProductCategory>>> get() = _productCategoryData

    private val _validateProductData = MutableLiveData<AddProductResult>()
    val validateProductData: LiveData<AddProductResult> get() = _validateProductData

    private val _productData = MutableLiveData<Result<Product>>()
    val productData: LiveData<Result<Product>> get() = _productData

    fun addOrUpdateProduct(
        categoryId: String,
        name: String,
        description: String,
        stock: String,
        price: String,
        priceCost: String,
        productId: String? = null,
        isUpdate: Boolean
    ) {
        viewModelScope.launch {
            delay(DELAY_TIME)
            validateDataProductUseCase(
                categoryId = categoryId,
                name = name,
                description = description,
                stock = stock,
                price = price,
                priceCost = priceCost
            ).collect { productResult ->
                when (productResult) {
                    is AddProductResult.Invalid -> _validateProductData.value = productResult
                    AddProductResult.Valid -> {
                        val product = Product(
                            id = if (isUpdate) productId ?: EMPTY_STRING else generateIdUseCase(),
                            categoryId = categoryId,
                            name = name,
                            description = description,
                            price = price.toDouble(),
                            priceCost = priceCost.toDouble(),
                            stock = stock.toInt(),
                            getCurrentDateUtc(),
                            getUserIdUseCase()
                        )
                        if (!isUpdate) {
                            addProductUseCase(product)
                        } else {
                            updateProductUseCase(product)
                        }
                        _validateProductData.value = productResult
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

    fun getProductById(productId: String) {
        viewModelScope.launch {
            getProductByIdUseCase(productId = productId).collect { productResult ->
                _productData.value = productResult
            }
        }
    }

    private fun getCurrentDateUtc(): String {
        return getDateUTCUseCase()
    }
}
