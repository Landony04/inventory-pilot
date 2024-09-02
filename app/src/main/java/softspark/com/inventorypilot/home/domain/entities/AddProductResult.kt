package softspark.com.inventorypilot.home.domain.entities

sealed class AddProductResult {
    data object Valid : AddProductResult()
    data class Invalid(val errorMessage: String) : AddProductResult()
}