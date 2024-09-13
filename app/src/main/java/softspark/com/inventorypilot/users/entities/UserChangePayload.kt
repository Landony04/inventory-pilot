package softspark.com.inventorypilot.users.entities

sealed interface UserChangePayload {

    data class Cellphone(val newCellphone: String) : UserChangePayload

    data class Status(val newStatus: String) : UserChangePayload
}
