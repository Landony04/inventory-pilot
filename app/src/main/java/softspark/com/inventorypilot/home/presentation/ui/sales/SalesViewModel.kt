package softspark.com.inventorypilot.home.presentation.ui.sales

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.domain.useCases.GenerateCurrentDateUTCUseCase
import softspark.com.inventorypilot.common.domain.useCases.GenerateIdUseCase
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants.PENDING_STATUS
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl.Companion.USER_ID_PREFERENCE
import softspark.com.inventorypilot.home.data.mapper.cart.toProductSale
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesByDateUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.InsertSaleUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val getSalesByDateUseCase: GetSalesByDateUseCase,
    private val insertSaleUseCase: InsertSaleUseCase,
    private val getDateUTCUseCase: GenerateCurrentDateUTCUseCase,
    private val generateIdUseCase: GenerateIdUseCase,
    private val inventoryPilotPreferences: InventoryPilotPreferences
) : ViewModel() {

    private val _salesData = MutableLiveData<Result<ArrayList<Sale>>>()
    val saleData: LiveData<Result<ArrayList<Sale>>> get() = _salesData

    fun getSalesByDate(date: String) {
        viewModelScope.launch {
            getSalesByDateUseCase(date).collect { result ->
                _salesData.value = result
            }
        }
    }

    fun getCurrentDate(format: String): String {
        val sdf = SimpleDateFormat(format, Locale.getDefault())
        return sdf.format(Date())
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertSale(cartItems: List<CartItem>, totalAmount: Double) {
        viewModelScope.launch {
            val sale = Sale(
                id = generateIdUseCase(),
                date = getDateUTCUseCase(),
                totalAmount = totalAmount,
                userId = inventoryPilotPreferences.getValuesString(USER_ID_PREFERENCE),
                products = ArrayList(cartItems.map { it.toProductSale() }),
                status = PENDING_STATUS
            )
            insertSaleUseCase(sale = sale)
        }
    }

    fun getCurrentDateUtc(): String {
        return getDateUTCUseCase()
    }
}