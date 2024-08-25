package softspark.com.inventorypilot.home.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.mapper.products.toCartItem
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import softspark.com.inventorypilot.home.domain.useCases.cart.AddProductToCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.DecreaseQuantityUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.EmptyCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.GetCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.IncreaseQuantityUseCase
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val addProductToCartUseCase: AddProductToCartUseCase,
    private val getCartUseCase: GetCartUseCase,
    private val emptyCartUseCase: EmptyCartUseCase,
    private val decreaseQuantityUseCase: DecreaseQuantityUseCase,
    private val increaseQuantityUseCase: IncreaseQuantityUseCase
) : ViewModel() {

    private val _addProductToCartData = MutableLiveData<Result<Boolean>>()
    val addProductToCartData: LiveData<Result<Boolean>> get() = _addProductToCartData

    private val _getCartData = MutableLiveData<Result<ArrayList<CartItem>>>()
    val getCartData: LiveData<Result<ArrayList<CartItem>>> get() = _getCartData

    private val _emptyCartData = MutableLiveData<Result<Boolean>>()
    val emptyCartData: LiveData<Result<Boolean>> get() = _emptyCartData

    fun addProductToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            addProductToCartUseCase(product.toCartItem(quantity)).collect { result ->
                _addProductToCartData.value = result
            }
        }
    }

    fun getCart() {
        viewModelScope.launch {
            getCartUseCase().collect { result ->
                _getCartData.value = result
            }
        }
    }

    fun emptyCart() {
        viewModelScope.launch {
            emptyCartUseCase().collect { result ->
                _emptyCartData.value = result
            }
        }
    }

    fun decreaseQuantity(cartItemId: String) {
        viewModelScope.launch {
            decreaseQuantityUseCase(cartItemId).collect()
        }
    }

    fun increaseQuantity(cartItemId: String) {
        viewModelScope.launch {
            increaseQuantityUseCase(cartItemId).collect()
        }
    }
}