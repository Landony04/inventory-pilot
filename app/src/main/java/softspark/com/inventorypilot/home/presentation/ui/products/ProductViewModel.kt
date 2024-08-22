package softspark.com.inventorypilot.home.presentation.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.QUERY_LENGTH
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ONE
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByCategoryIdUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByNameUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsUseCase
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase,
    private val getProductsByCategoryIdUseCase: GetProductsByCategoryIdUseCase,
    private val getProductsByNameUseCase: GetProductsByNameUseCase
) : ViewModel() {

    private val _productCategoryData = MutableLiveData<Result<ArrayList<ProductCategory>>>()
    val productCategoryData: LiveData<Result<ArrayList<ProductCategory>>> get() = _productCategoryData

    private val _productsData = MutableLiveData<Result<ArrayList<Product>>>()
    val productsData: LiveData<Result<ArrayList<Product>>> get() = _productsData

    private var currentPage: Int = VALUE_ONE

    fun getAllProducts() {
        currentPage++
        viewModelScope.launch {
            getProductsUseCase(currentPage).collect { result ->
                _productsData.value = result
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

    fun getProductsByCategoryId(categoryId: String) {
        viewModelScope.launch {
            getProductsByCategoryIdUseCase(categoryId).collect { result ->
                _productsData.value = result
            }
        }
    }

    fun getProductsByName(query: String) {
        viewModelScope.launch {
            if (query.length >= QUERY_LENGTH) {
                getProductsByNameUseCase(query).collect { result ->
                    _productsData.value = result
                }
            }
        }
    }
}
