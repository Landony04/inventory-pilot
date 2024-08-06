package softspark.com.inventorypilot.login.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.databinding.LoginActivityBinding
import softspark.com.inventorypilot.login.presentation.viewModel.LoginViewModel

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        setUpObservers()
    }

    private fun initListeners() = with(binding) {
        loginButton.setOnClickListener {
            doLogin()
        }
    }

    private fun setUpObservers() {
        loginViewModel.loginData.observe(this) { result ->
            if (result.isSuccess) {
                println("Hemos hecho login")
            } else {
                println("Fallo el login")
            }
        }
    }

    private fun doLogin() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        loginViewModel.login(email, password)
    }
}