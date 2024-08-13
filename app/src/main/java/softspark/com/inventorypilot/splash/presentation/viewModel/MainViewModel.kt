package softspark.com.inventorypilot.splash.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.splash.domain.useCases.GetUserIdUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserIdUseCase: GetUserIdUseCase
) : ViewModel() {

    private val _userIdData = MutableLiveData<String?>()
    val userIdData: LiveData<String?> get() = _userIdData

    fun getUserId() {
        viewModelScope.launch {
//            delay(200L)
            getUserIdUseCase().collect { userId ->
                _userIdData.value = userId
            }
        }
    }
}