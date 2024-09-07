package softspark.com.inventorypilot.login.domain.models

data class UserProfile(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    val cellPhone: String
)
