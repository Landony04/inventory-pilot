package softspark.com.inventorypilot.login.data.mapper

import softspark.com.inventorypilot.common.data.local.entity.BranchEntity
import softspark.com.inventorypilot.common.data.local.entity.UserProfileEntity
import softspark.com.inventorypilot.login.domain.models.Branch
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.remote.dto.UserProfileResponse
import softspark.com.inventorypilot.login.remote.dto.branches.BranchesResponse

fun UserProfileResponse.toDomain(): List<UserProfile> {
    return entries.map {
        val id = it.key
        val dto = it.value

        UserProfile(
            id = id,
            email = dto.email,
            firstName = dto.firstName,
            lastName = dto.lastName,
            role = dto.role,
            cellPhone = dto.cellPhone,
            status = dto.status,
            branchId = dto.branchId
        )
    }
}

fun UserProfileEntity.toUserProfile(): UserProfile = UserProfile(
    id = userId,
    email = email,
    firstName = firstName,
    lastName = lastName,
    role = role,
    cellPhone = cellPhone,
    status = status,
    branchId = branchId
)

fun UserProfile.toEntity(): UserProfileEntity {
    return UserProfileEntity(
        userId = id,
        email = email,
        firstName = firstName,
        lastName = lastName,
        role = role,
        cellPhone = cellPhone,
        status = status,
        branchId = branchId
    )
}

fun BranchesResponse.toBranchesDomain(): List<Branch> {
    return entries.map {
        val id = it.key
        val dto = it.value

        Branch(
            id = id,
            name = dto.name,
            address = dto.address
        )
    }
}

fun Branch.toBranchEntity(): BranchEntity = BranchEntity(
    id = id,
    name = name,
    address = address
)