package softspark.com.inventorypilot.common.utils.preferences

interface InventoryPilotPreferences {

    fun clearPreferences()

    fun initPreferences()

    fun setValuesString(key: String, value: String)

    fun getValuesString(key: String): String
}
