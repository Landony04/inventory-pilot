package softspark.com.inventorypilot.home.presentation.ui.sales

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants
import softspark.com.inventorypilot.common.utils.Constants.PENDING_STATUS
import softspark.com.inventorypilot.common.utils.Constants.UTC_DATE_FORMAT
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.home.data.mapper.cart.toProductSale
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesByDateUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.InsertSaleUseCase
import softspark.com.inventorypilot.login.domain.repositories.AuthenticationRepositoryImpl.Companion.USER_ID_PREFERENCE
import java.text.SimpleDateFormat
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val getSalesUseCase: GetSalesUseCase,
    private val getSalesByDateUseCase: GetSalesByDateUseCase,
    private val insertSaleUseCase: InsertSaleUseCase,
    private val inventoryPilotPreferences: InventoryPilotPreferences
) : ViewModel() {

    private val _salesData = MutableLiveData<Result<ArrayList<Sale>>>()
    val saleData: LiveData<Result<ArrayList<Sale>>> get() = _salesData

    private var currentPage: Int = Constants.VALUE_ONE

    fun getSalesForPage() {
        currentPage++
        viewModelScope.launch {
            getSalesUseCase(currentPage).collect { result ->
                _salesData.value = result
            }
        }
    }

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
    fun getCurrentUtcDate(): String {
        val currentDateTime = ZonedDateTime.now(ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern(UTC_DATE_FORMAT)
        return currentDateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun insertSale(cartItems: List<CartItem>, totalAmount: Double) {
        viewModelScope.launch {
            val sale = Sale(
                id = UUID.randomUUID().toString(),
                date = getCurrentUtcDate(),
                totalAmount = totalAmount,
                userId = inventoryPilotPreferences.getValuesString(USER_ID_PREFERENCE),
                products = ArrayList(cartItems.map { it.toProductSale() }),
                status = PENDING_STATUS
            )
            insertSaleUseCase(sale = sale)
        }
    }
}