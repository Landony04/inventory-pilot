package softspark.com.inventorypilot.users.presentation.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidateEmailUseCase
import softspark.com.inventorypilot.users.domain.entities.AddUserResult
import softspark.com.inventorypilot.users.domain.useCases.AddUserUseCase
import softspark.com.inventorypilot.users.domain.useCases.ValidateUserUseCase
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    private val addUserUseCase: AddUserUseCase,
    private val validateUserProfileUseCase: ValidateUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
) : ViewModel() {

    private val _validateUserData = MutableLiveData<AddUserResult>()
    val validateUserData: LiveData<AddUserResult> get() = _validateUserData

    fun addUser(
        email: String,
        firstName: String,
        lastName: String,
        role: String,
        cellPhone: String
    ) {
        viewModelScope.launch {
            validateEmailUseCase(email).collect { isValid ->
                if (isValid) {
                    validateUserProfileUseCase(
                        email, firstName, lastName, role, cellPhone
                    ).collect { validateResult ->
                        when (validateResult) {
                            is AddUserResult.Invalid -> _validateUserData.value = validateResult
                            AddUserResult.Valid -> addUserUseCase(
                                UserProfile(
                                    id = "",
                                    email = email,
                                    firstName = firstName,
                                    lastName = lastName,
                                    role = role,
                                    cellPhone = cellPhone
                                )
                            )
                        }
                    }
                } else {
                    _validateUserData.value = AddUserResult.Invalid("Correo invalido.")
                }
            }
        }
    }
}