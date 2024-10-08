package softspark.com.inventorypilot.home.presentation.ui.products.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.QUERY_LENGTH
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.domain.useCases.addCategoryProduct.SyncCategoryProductUseCase
import softspark.com.inventorypilot.home.domain.useCases.addProduct.SyncProductsUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductCategoriesUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByCategoryIdUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsByNameUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsFromApiUseCase
import softspark.com.inventorypilot.home.domain.useCases.products.GetProductsUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.SyncSalesUseCase
import softspark.com.inventorypilot.users.domain.useCases.SyncUserUseCase
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val getProductsFromApiUseCase: GetProductsFromApiUseCase,
    private val getProductCategoriesUseCase: GetProductCategoriesUseCase,
    private val getProductsByCategoryIdUseCase: GetProductsByCategoryIdUseCase,
    private val getProductsByNameUseCase: GetProductsByNameUseCase,
    private val syncSalesUseCase: SyncSalesUseCase,
    private val syncProductsUseCase: SyncProductsUseCase,
    private val syncUserUseCase: SyncUserUseCase,
    private val syncCategoryProductUseCase: SyncCategoryProductUseCase
) : ViewModel() {

    init {
        getProductCategories()
        syncProductsFromApi()
        syncSales()
        syncCategoryProduct()
        syncProducts()
        syncUsers()
    }

    private val _productCategoryData = MutableLiveData<Result<ArrayList<ProductCategory>>>()
    val productCategoryData: LiveData<Result<ArrayList<ProductCategory>>> get() = _productCategoryData

    private val _productsData = MutableStateFlow<List<Product>>(emptyList())
    val productsData: StateFlow<List<Product>> get() = _productsData.asStateFlow()

    private val _loadingState = MutableStateFlow(false)
    val loadingState: StateFlow<Boolean> = _loadingState

    private var currentPage = 0
    private val pageSize = 20
    private var isLoading = false
    private var endReached = false

    fun resetValues() {
        currentPage = 0
        isLoading = false
        endReached = false
        _productsData.value = emptyList()
    }

    fun getAllProducts() {

        if (isLoading || endReached) return

        viewModelScope.launch(Dispatchers.IO) {
            getProductsUseCase(pageSize, currentPage).collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        is Result.Error -> {
                            isLoading = false
                            _loadingState.value = false
                        }

                        is Result.Success -> {
                            isLoading = false
                            _loadingState.value = false

                            if (it.data.isEmpty()) {
                                endReached = true
                            } else {
                                _productsData.value += it.data
                                currentPage++
                            }
                        }

                        Result.Loading -> {
                            isLoading = true
                            _loadingState.value = true
                        }
                    }
                }
            }
        }
    }

    private fun getProductCategories() {
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

    private fun syncProductsFromApi() {
        viewModelScope.launch {
            getProductsFromApiUseCase()
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
