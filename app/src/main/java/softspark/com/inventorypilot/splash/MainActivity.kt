package softspark.com.inventorypilot.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import softspark.com.inventorypilot.databinding.MainActivityBinding
import softspark.com.inventorypilot.login.presentation.ui.LoginActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

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

        // Navegar a la siguiente actividad después de la pantalla de bienvenida
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}