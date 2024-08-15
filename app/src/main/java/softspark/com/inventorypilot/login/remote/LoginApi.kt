package softspark.com.inventorypilot.login.remote

import retrofit2.http.GET
import retrofit2.http.PATCH
import softspark.com.inventorypilot.login.remote.dto.UserProfileDto
import softspark.com.inventorypilot.login.remote.dto.UserProfileResponse

interface LoginApi {
    companion object {
        const val BASE_URL = "https://inventory-pilot-default-rtdb.firebaseio.com/"
    }

    @GET("businesses/business_info_id_1/users.json")
    suspend fun getUserProfile(): UserProfileResponse

    @PATCH("businesses/business_info_id_1/users.json")
    suspend fun insertUser(userProfileDto: UserProfileDto)
}