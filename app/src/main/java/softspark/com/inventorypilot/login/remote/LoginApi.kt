package softspark.com.inventorypilot.login.remote

import retrofit2.http.GET
import softspark.com.inventorypilot.login.remote.dto.UserProfileResponse
import softspark.com.inventorypilot.login.remote.dto.branches.BranchesResponse

interface LoginApi {

    @GET("businesses/users.json")
    suspend fun getUserProfile(): UserProfileResponse

    @GET("businesses/branches.json")
    suspend fun getBranches(): BranchesResponse?
}