package softspark.com.inventorypilot.splash

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.databinding.MainActivityBinding
import softspark.com.inventorypilot.navigation.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        splashScreen.setKeepOnScreenCondition {
            // Aquí puedes agregar condiciones para mantener la pantalla de bienvenida visible
            // Por ejemplo, esperar la inicialización de datos
            false
        }

        navigateToLogin()
    }

    private fun navigateToLogin() {
        // Navegar a la siguiente actividad después de la pantalla de bienvenida
        navigator.navigateToLogin()
    }
}