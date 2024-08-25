package softspark.com.inventorypilot.home.presentation.ui.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.common.utils.Constants
import softspark.com.inventorypilot.common.utils.Constants.DD_MMM_DATE_FORMAT
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesByDateUseCase
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesUseCase
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val getSalesUseCase: GetSalesUseCase,
    private val getSalesByDateUseCase: GetSalesByDateUseCase
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

    fun getCurrentDate(): String {
        val sdf = SimpleDateFormat(DD_MMM_DATE_FORMAT, Locale.getDefault())
        return sdf.format(Date())
    }
}