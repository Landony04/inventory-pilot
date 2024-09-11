package softspark.com.inventorypilot.home.domain.entities

sealed class AddCategoryProductResult {
    data object Valid : AddCategoryProductResult()
    data class Invalid(val errorMessage: String) : AddCategoryProductResult()
}