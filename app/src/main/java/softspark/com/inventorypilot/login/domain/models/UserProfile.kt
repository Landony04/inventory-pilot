package softspark.com.inventorypilot.login.domain.models

data class UserProfile(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val role: String,
    var cellPhone: String,
    var status: String,
    val branchId: String
)
