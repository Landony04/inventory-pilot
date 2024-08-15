package softspark.com.inventorypilot.login.data.mapper

import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.remote.dto.UserProfileResponse

fun UserProfileResponse.toDomain(email: String): UserProfile {
    return entries.map {
        val id = it.key
        val dto = it.value

        UserProfile(
            id = id,
            email = dto.email,
            firstName = dto.firstName,
            lastName = dto.lastName,
            role = dto.role,
            uid = dto.uid
        )
    }.first { it.email == email }
}