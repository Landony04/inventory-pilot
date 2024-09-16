package softspark.com.inventorypilot.login.remote

import retrofit2.http.GET
import softspark.com.inventorypilot.login.remote.dto.UserProfileResponse

interface LoginApi {
    companion object {
        const val BASE_URL = "https://inventory-pilot-default-rtdb.firebaseio.com/"
    }

    @GET("businesses/users.json")
    suspend fun getUserProfile(): UserProfileResponse
}