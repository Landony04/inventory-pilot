package softspark.com.inventorypilot.home.presentation.ui.profits.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.useCases.profits.GetDailyProfitsUseCase
import softspark.com.inventorypilot.home.domain.useCases.profits.GetMonthlyProfitsUseCase
import javax.inject.Inject

@HiltViewModel
class ProfitsViewModel @Inject constructor(
    private val getDailyProfitsUseCase: GetDailyProfitsUseCase,
    private val getMonthlyProfitsUseCase: GetMonthlyProfitsUseCase
) : ViewModel() {

    private val _profits = MutableLiveData<Result<Map<String, Double>>>()
    val profits: LiveData<Result<Map<String, Double>>> get() = _profits

    fun getDailyProfits(date: String) {
        viewModelScope.launch {
            getDailyProfitsUseCase(date).collect { result ->
                _profits.value = result
            }
        }
    }

    fun getMonthlyProfits(month: String) {
        viewModelScope.launch {
            getMonthlyProfitsUseCase(month).collect { result ->
                _profits.value = result
            }
        }
    }
}