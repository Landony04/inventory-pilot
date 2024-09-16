package softspark.com.inventorypilot.users.presentation.viewModel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.domain.useCases.GenerateIdUseCase
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.domain.useCases.authentication.ValidateEmailUseCase
import softspark.com.inventorypilot.users.domain.entities.AddUserResult
import softspark.com.inventorypilot.users.domain.useCases.AddUserUseCase
import softspark.com.inventorypilot.users.domain.useCases.ValidateUserUseCase
import softspark.com.inventorypilot.users.utils.UserConstants
import javax.inject.Inject

@HiltViewModel
class AddUserViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val addUserUseCase: AddUserUseCase,
    private val generateIdUseCase: GenerateIdUseCase,
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
        cellPhone: String,
        branchId: String
    ) {
        viewModelScope.launch {
            validateEmailUseCase(email).collect { isValid ->
                if (isValid) {
                    validateUserProfileUseCase(
                        email, firstName, lastName, role, cellPhone
                    ).collect { validateResult ->
                        when (validateResult) {
                            is AddUserResult.Invalid -> _validateUserData.value = validateResult

                            AddUserResult.Valid -> {
                                addUserUseCase(
                                    UserProfile(
                                        id = generateIdUseCase(),
                                        email = email,
                                        firstName = firstName,
                                        lastName = lastName,
                                        role = role,
                                        cellPhone = cellPhone,
                                        status = UserConstants.USER_STATUS_ENABLED,
                                        branchId = branchId
                                    )
                                )
                                _validateUserData.value = validateResult
                            }
                        }
                    }
                } else {
                    _validateUserData.value =
                        AddUserResult.Invalid(context.getString(R.string.text_error_invalid_email))
                }
            }
        }
    }
}