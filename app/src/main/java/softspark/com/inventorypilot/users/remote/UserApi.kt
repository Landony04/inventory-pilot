package softspark.com.inventorypilot.users.remote

import retrofit2.http.Body
import retrofit2.http.PATCH
import softspark.com.inventorypilot.users.remote.dto.user.AddUserRequest

interface UserApi {

    @PATCH("businesses/business_info_id_1/users.json")
    suspend fun addUser(@Body addUserRequest: AddUserRequest)
}
