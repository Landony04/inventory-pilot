package softspark.com.inventorypilot.users.domain.useCases

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.utils.Constants.VALUE_ZERO
import softspark.com.inventorypilot.users.domain.entities.AddUserResult
import javax.inject.Inject

class ValidateUserUseCaseImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ValidateUserUseCase {
    override suspend fun invoke(
        email: String,
        firstName: String,
        lastName: String,
        role: String,
        cellPhone: String,
        branch: String
    ): Flow<AddUserResult> = flow {

        if (firstName.isEmpty()) {
            return@flow emit(AddUserResult.Invalid(context.getString(R.string.text_error_without_first_name)))
        }

        if (lastName.isEmpty()) {
            return@flow emit(AddUserResult.Invalid(context.getString(R.string.text_error_without_last_name)))
        }

        if (role.isEmpty() || role == context.getString(R.string.text_all_roles)) {
            return@flow emit(AddUserResult.Invalid(context.getString(R.string.text_error_without_role)))
        }

        if (cellPhone.isEmpty()) {
            return@flow emit(AddUserResult.Invalid(context.getString(R.string.text_error_without_cellphone)))
        }

        if (email.isEmpty()) {
            return@flow emit(AddUserResult.Invalid(context.getString(R.string.text_error_without_email)))
        }

        if (branch.isEmpty() || branch == VALUE_ZERO.toString()) {
            return@flow emit(AddUserResult.Invalid(context.getString(R.string.text_error_without_branch)))
        }

        return@flow emit(AddUserResult.Valid)
    }
}