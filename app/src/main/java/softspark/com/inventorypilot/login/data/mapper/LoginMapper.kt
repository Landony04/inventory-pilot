package softspark.com.inventorypilot.login.data.mapper

import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity
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
            role = dto.role
        )
    }.first { it.email == email }
}

fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        id = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        role = role
    )
}

fun UserProfileEntity.toUserProfileDomain(): UserProfile = UserProfile(
    id = id,
    email = email,
    firstName = firstName,
    lastName = lastName,
    role = role
)

fun UserProfileResponse.toUserProfile(): UserProfile {
    return this.entries.map {

        val dto = it.value

        UserProfile(
            id = it.key,
            email = dto.email,
            firstName = dto.firstName,
            lastName = dto.lastName,
            role = dto.role
        )
    }.first()
}