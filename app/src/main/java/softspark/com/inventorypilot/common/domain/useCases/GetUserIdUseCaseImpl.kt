package softspark.com.inventorypilot.common.domain.useCases

import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferences
import softspark.com.inventorypilot.common.utils.preferences.InventoryPilotPreferencesImpl
import javax.inject.Inject

class GetUserIdUseCaseImpl @Inject constructor(
    private val inventoryPilotPreferences: InventoryPilotPreferences
) : GetUserIdUseCase {
    override fun invoke(): String = inventoryPilotPreferences.getValuesString(
        InventoryPilotPreferencesImpl.USER_ID_PREFERENCE
    )
}
