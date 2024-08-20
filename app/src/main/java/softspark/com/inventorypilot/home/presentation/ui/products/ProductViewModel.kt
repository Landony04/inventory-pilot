package softspark.com.inventorypilot.home.presentation.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsUseCase
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase
) : ViewModel() {

    private val _productCategoryData = MutableLiveData<Result<ArrayList<ProductCategory>>>()
    val productCategoryData: LiveData<Result<ArrayList<ProductCategory>>> get() = _productCategoryData

    private val _productsData = MutableLiveData<Result<ArrayList<Product>>>()
    val productsData: LiveData<Result<ArrayList<Product>>> get() = _productsData

    private var currentPage: Int = 1

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
}
