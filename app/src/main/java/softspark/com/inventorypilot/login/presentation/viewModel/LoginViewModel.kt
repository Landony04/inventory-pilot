package softspark.com.inventorypilot.login.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.domain.entities.PasswordResult
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.domain.useCases.authentication.GetBranchesUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.GetUserProfileUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.LoginUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidateEmailUseCase
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidatePasswordUseCase
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getUserProfileUseCase: GetUserProfileUseCase,
    private val loginUseCase: LoginUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val getBranchesUseCase: GetBranchesUseCase
) : ViewModel() {

    private val _userProfileData = MutableLiveData<Result<UserProfile>>()
    val userProfileData: LiveData<Result<UserProfile>> get() = _userProfileData

    private val _loginData = MutableLiveData<Result<Unit>>()
    val loginData: LiveData<Result<Unit>> get() = _loginData

    private val _emailValidateData = MutableLiveData<Boolean>()
    val emailValidateData: LiveData<Boolean> get() = _emailValidateData

    private val _passwordValidateData = MutableLiveData<PasswordResult>()
    val passwordValidateData: LiveData<PasswordResult> get() = _passwordValidateData

    fun getUserProfile(email: String) {
        viewModelScope.launch {
            getUserProfileUseCase(email).collect { userProfile ->
                _userProfileData.value = userProfile
            }
        }
    }

    fun login(email: String, password: String) {
        viewModelScope.launch {
            loginUseCase(email, password).collect { result ->
                _loginData.value = result
            }
        }
    }

    fun validateEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            validateEmailUseCase(email).collect { isValid ->
                if (isValid) {
                    _emailValidateData.value = true
                    validatePasswordUseCase(password).collect { passwordResult ->
                        _passwordValidateData.value = passwordResult
                    }
                } else {
                    _emailValidateData.value = false
                }
            }
        }
    }

    fun getBranches() {
        viewModelScope.launch {
            getBranchesUseCase()
        }
    }
}