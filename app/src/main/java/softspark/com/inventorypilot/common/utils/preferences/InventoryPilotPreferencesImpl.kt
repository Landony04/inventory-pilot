package softspark.com.inventorypilot.common.utils.preferences

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.common.utils.Constants.EMPTY_STRING
import javax.inject.Inject

class InventoryPilotPreferencesImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : InventoryPilotPreferences {

    private lateinit var sharedPreferences: SharedPreferences
    private val SHARED_PREFERENCES = "INVENTORY_PILOT_PREFERENCES"

    companion object {
        //Preferences
        const val USER_ID_PREFERENCE = "user_id"
        const val USER_ROLE_PREFERENCE = "role"
    }

    init {
        initPreferences()
    }

    override fun initPreferences() {
        sharedPreferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }

    override fun setValuesString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    override fun getValuesString(key: String): String {
        return sharedPreferences.getString(key, EMPTY_STRING) ?: EMPTY_STRING
    }
}
