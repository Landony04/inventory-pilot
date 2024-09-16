package softspark.com.inventorypilot.users.remote

import retrofit2.http.Body
import retrofit2.http.PATCH
import retrofit2.http.Path
import softspark.com.inventorypilot.users.remote.dto.user.AddUserRequest
import softspark.com.inventorypilot.users.remote.dto.user.ModifiedUserRequest

interface UserApi {

    @PATCH("businesses/users.json")
    suspend fun addUser(@Body addUserRequest: AddUserRequest)

    @PATCH("businesses/users/{id}.json")
    suspend fun changeUserStatus(
        @Path("id") userId: String,
        @Body modifiedUserRequest: ModifiedUserRequest
    )
}
