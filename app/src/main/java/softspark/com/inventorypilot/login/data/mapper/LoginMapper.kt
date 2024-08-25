package softspark.com.inventorypilot.login.data.mapper

import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.remote.dto.UserProfileResponse

fun UserProfileResponse.toDomain(): List<UserProfile> {
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
    }
}

fun UserProfileEntity.toUserProfile(): UserProfile = UserProfile(
    id = userId,
    email = email,
    firstName = firstName,
    lastName = lastName,
    role = role
)

fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        userId = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        role = role
    )
}

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