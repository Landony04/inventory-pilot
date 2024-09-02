package softspark.com.inventorypilot.users.domain.entities

sealed class AddUserResult {
    data object Valid : AddUserResult()

    data class Invalid(val errorMessage: String) : AddUserResult()
}
