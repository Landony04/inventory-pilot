package softspark.com.inventorypilot.login.remote.dto

data class UserProfileDto(
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val cellPhone: String,
    val status: String,
    val branchId: String
)
