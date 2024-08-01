package softspark.com.inventorypilot.login.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import softspark.com.inventorypilot.databinding.LoginActivityBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}