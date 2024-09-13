package softspark.com.inventorypilot.users.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.users.domain.useCases.EnabledOrDisabledUserUseCase
import softspark.com.inventorypilot.users.domain.useCases.GetAllUsersUseCase
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase,
    private val enabledOrDisabledUserUseCase: EnabledOrDisabledUserUseCase
) : ViewModel() {

    private val _getUsersData = MutableLiveData<Result<List<UserProfile>>>()
    val getUsersData: LiveData<Result<List<UserProfile>>> get() = _getUsersData

    fun getAllUsers() {
        viewModelScope.launch {
            getAllUsersUseCase().collect { result ->
                _getUsersData.value = result
            }
        }
    }

    fun updateUserStatus(user: UserProfile) {
        viewModelScope.launch {
            enabledOrDisabledUserUseCase(user)
        }
    }
}