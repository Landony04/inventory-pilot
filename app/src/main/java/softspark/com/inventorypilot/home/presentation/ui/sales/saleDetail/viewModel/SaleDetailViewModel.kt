package softspark.com.inventorypilot.home.presentation.ui.sales.saleDetail.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.domain.useCases.sales.GetSaleByIdUseCase
import javax.inject.Inject

@HiltViewModel
class SaleDetailViewModel @Inject constructor(
    private val getSaleByIdUseCase: GetSaleByIdUseCase
) : ViewModel() {

    private val _saleData = MutableLiveData<Result<Sale>>()
    val saleData: LiveData<Result<Sale>> get() = _saleData

    fun getSaleById(saleId: String) {
        viewModelScope.launch {
            getSaleByIdUseCase(saleId).collect { result ->
                _saleData.value = result
            }
        }
    }
}