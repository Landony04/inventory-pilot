package softspark.com.inventorypilot.home.presentation.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.mapper.products.toCartItem
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import softspark.com.inventorypilot.home.domain.useCases.cart.AddProductToCartUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.DecreaseQuantityUseCase
import softspark.com.inventorypilot.home.domain.useCases.cart.DeleteCartItemUseCase
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
    private val increaseQuantityUseCase: IncreaseQuantityUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase
) : ViewModel() {

    private val _getCartData = MediatorLiveData<Result<ArrayList<CartItem>>>()
    val getCartData: LiveData<Result<ArrayList<CartItem>>> get() = _getCartData

    private val _emptyCartData = MediatorLiveData<Result<Boolean>>()
    val emptyCartData: LiveData<Result<Boolean>> get() = _emptyCartData

    fun addProductToCart(product: Product, quantity: Int) {
        viewModelScope.launch {
            addProductToCartUseCase(product.toCartItem(quantity))
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
            decreaseQuantityUseCase(cartItemId)
        }
    }

    fun increaseQuantity(cartItemId: String) {
        viewModelScope.launch {
            increaseQuantityUseCase(cartItemId)
        }
    }

    fun deleteItemCart(cartItemId: String) {
        viewModelScope.launch {
            deleteCartItemUseCase(cartItemId)
        }
    }
}