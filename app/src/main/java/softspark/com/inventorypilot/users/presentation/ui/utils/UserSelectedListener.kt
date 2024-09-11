package softspark.com.inventorypilot.users.presentation.ui.utils

import softspark.com.inventorypilot.login.domain.models.UserProfile

interface UserSelectedListener {
    fun disableOrEnabledUser(user: UserProfile)
}