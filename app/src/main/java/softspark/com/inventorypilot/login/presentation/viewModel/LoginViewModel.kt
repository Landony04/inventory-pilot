package softspark.com.inventorypilot.login.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.login.domain.useCases.authentication.LoginUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidateEmailUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    private val _loginData = MutableLiveData<Result<Unit>>()
    val loginData: LiveData<Result<Unit>> get() = _loginData

    private val _emailValidateData = MutableLiveData<Boolean>()
    val emailValidateData: LiveData<Boolean> get() = _emailValidateData

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                _loginData.value = result
            }
        }
    }

    fun validateEmail(email: String) {
        viewModelScope.launch {
            validateEmailUseCase(email).collect { isValid ->
                _emailValidateData.value = isValid
            }
        }
    }
}