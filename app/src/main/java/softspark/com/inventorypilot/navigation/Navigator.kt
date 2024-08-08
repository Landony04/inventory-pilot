package softspark.com.inventorypilot.navigation

import android.content.Context
import android.content.Intent
import dagger.hilt.android.qualifiers.ApplicationContext
import softspark.com.inventorypilot.home.presentation.ui.home.HomeActivity
import softspark.com.inventorypilot.login.presentation.ui.LoginActivity
import javax.inject.Inject

class Navigator @Inject constructor(@ApplicationContext private val context: Context) {

    fun navigateToHome() {
        val intent = Intent(context, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    fun navigateToLogin() {
        val intent = Intent(context, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}