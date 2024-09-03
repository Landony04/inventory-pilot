package softspark.com.inventorypilot.users.data.mapper

import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.users.data.local.entity.user.UserSyncEntity
import softspark.com.inventorypilot.users.remote.dto.user.AddUserRequest
import softspark.com.inventorypilot.users.remote.dto.user.UserDto

fun UserProfile.toAddUserRequest(id: String): AddUserRequest {
    return mapOf(id to this.toUserDto())
}

fun UserProfile.toUserDto(): UserDto = UserDto(
    email = email,
    firstName = firstName,
    lastName = lastName,
    role = role,
    cellPhone = cellPhone
)

fun UserProfile.toUserSync(): UserSyncEntity = UserSyncEntity(id = id)
