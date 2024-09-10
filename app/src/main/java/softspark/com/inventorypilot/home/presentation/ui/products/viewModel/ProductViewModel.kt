package softspark.com.inventorypilot.home.presentation.ui.products.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.QUERY_LENGTH
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ZERO
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct.SyncCategoryProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.addProduct.SyncProductsUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByCategoryIdUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByNameUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.SyncSalesUseCase
import softspark.com.inventorypilot.users.domain.useCases.SyncUserUseCase
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase,
    private val getProductsByCategoryIdUseCase: GetProductsByCategoryIdUseCase,
    private val getProductsByNameUseCase: GetProductsByNameUseCase,
    private val syncSalesUseCase: SyncSalesUseCase,
    private val syncProductsUseCase: SyncProductsUseCase,
    private val syncUserUseCase: SyncUserUseCase,
    private val syncCategoryProductUseCase: SyncCategoryProductUseCase
) : ViewModel() {

    init {
        syncSales()
        syncCategoryProduct()
        syncProducts()
        syncUsers()
    }

    private val _productCategoryData = MutableLiveData<Result<ArrayList<ProductCategory>>>()
    val productCategoryData: LiveData<Result<ArrayList<ProductCategory>>> get() = _productCategoryData

    private val _productsData = MutableLiveData<List<Product>>()
    val productsData: LiveData<List<Product>> get() = _productsData

    private var offset = VALUE_ZERO
    private var limit = 20
    var isLoading = false
        private set

    fun resetValues() {
        _productsData.value = emptyList()
        offset = VALUE_ZERO
        limit = 20
    }

    fun getAllProducts() {
        if (isLoading) return

        isLoading = true
        viewModelScope.launch {
            val products = getProductsUseCase(limit, offset)
            if (products.isNotEmpty()) {
                _productsData.value = _productsData.value.orEmpty() + products
                offset += limit
            }
            isLoading = false
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
            val products = getProductsByCategoryIdUseCase(categoryId)
            _productsData.value = products
        }
    }

    fun getProductsByName(query: String) {
        viewModelScope.launch {
            if (query.length >= QUERY_LENGTH) {
                val products = getProductsByNameUseCase(query)
                _productsData.value = products
            }
        }
    }

    private fun syncSales() {
        viewModelScope.launch {
            syncSalesUseCase()
        }
    }

    private fun syncProducts() {
        viewModelScope.launch {
            syncProductsUseCase()
        }
    }

    private fun syncUsers() {
        viewModelScope.launch {
            syncUserUseCase()
        }
    }

    private fun syncCategoryProduct() {
        viewModelScope.launch {
            syncCategoryProductUseCase()
        }
    }
}
