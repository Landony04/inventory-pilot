package softspark.com.inventorypilot.home.presentation.ui.products.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.domain.useCases.session.LogoutUseCase
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : ViewModel() {

    private val _logoutData = MutableLiveData<Result<Boolean>>()
    val logoutData: LiveData<Result<Boolean>> get() = _logoutData

    fun doLogout() {
        viewModelScope.launch {
            logoutUseCase().collect { result ->
                _logoutData.value = result
            }
        }
    }
}
