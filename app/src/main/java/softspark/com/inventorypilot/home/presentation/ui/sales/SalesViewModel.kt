package softspark.com.inventorypilot.home.presentation.ui.sales

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSalesUseCase
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val getSalesUseCase: GetSalesUseCase
) : ViewModel() {

    private val _salesData = MutableLiveData<Result<ArrayList<Sale>>>()
    val saleData: LiveData<Result<ArrayList<Sale>>> get() = _salesData

    fun getAllSales() {
        viewModelScope.launch {
            getSalesUseCase().collect { result ->
                _salesData.value = result
            }
        }
    }
}