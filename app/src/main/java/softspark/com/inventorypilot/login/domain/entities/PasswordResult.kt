package softspark.com.inventorypilot.login.domain.entities

sealed class PasswordResult {
    data object Valid: PasswordResult()
    data class Invalid(val errorMessage: String): PasswordResult()
}