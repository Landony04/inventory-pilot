package softspark.com.inventorypilot.login.domain.useCases.authentication

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.login.domain.entities.PasswordResult
import javax.inject.Inject

class ValidatePasswordUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ValidatePasswordUseCase {

    companion object {
        const val SIZE_VALID_FOR_PASSWORD = 8
    }

    override fun invoke(password: String): Flow<PasswordResult> {
        return flow {
            if (password.length < SIZE_VALID_FOR_PASSWORD) {
                return@flow emit(PasswordResult.Invalid(context.getString(R.string.text_error_size_password)))
            }

            if (!password.any { pass -> pass.isLowerCase() }) {
                return@flow emit(PasswordResult.Invalid(context.getString(R.string.text_error_lower_case_password)))
            }

            if (!password.any { pass -> pass.isUpperCase() }) {
                return@flow emit(PasswordResult.Invalid(context.getString(R.string.text_error_upper_case_password)))
            }

            if (!password.any { pass -> pass.isDigit() }) {
                return@flow emit(PasswordResult.Invalid(context.getString(R.string.text_error_digit_password)))
            }

            return@flow emit(PasswordResult.Valid)
        }
    }
}