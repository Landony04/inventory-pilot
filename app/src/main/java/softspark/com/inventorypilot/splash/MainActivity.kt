package softspark.com.inventorypilot.splash

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.databinding.MainActivityBinding
import softspark.com.inventorypilot.navigation.Navigator
import softspark.com.inventorypilot.splash.presentation.viewModel.MainViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    private lateinit var navigator: Navigator

    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        navigator = Navigator()

        setUpObservers()

        splashScreen.setKeepOnScreenCondition { true }

        validateUserId()
    }

    private fun setUpObservers() {
        mainViewModel.userIdData.observe(this) { userId ->
            navigateToLoginOrHome(userId)
        }
    }

    private fun navigateToLoginOrHome(userId: String?) {
        if (userId != null) {
            // Navigate to home
            navigator.navigateToHome(this)
        } else {
            // Navigate to login
            navigator.navigateToLogin(this)
        }
        finish()
    }

    private fun validateUserId() {
        mainViewModel.getUserId()
    }
}