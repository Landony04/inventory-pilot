package softspark.com.inventorypilot.navigation

import android.content.Context
import android.content.Intent
import softspark.com.inventorypilot.home.presentation.ui.home.HomeActivity
import softspark.com.inventorypilot.login.presentation.ui.LoginActivity

class Navigator {

    fun navigateToHome(context: Context) {
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }

    fun navigateToLogin(context: Context) {
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
    }
}