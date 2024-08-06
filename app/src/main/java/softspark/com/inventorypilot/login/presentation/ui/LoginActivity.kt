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

    private fun doLogin() {
        val password = binding.passwordEditText.text.toString()
        loginViewModel.login(getEmail(), password)
    }

    private fun getEmail(): String = binding.emailEditText.text.toString()

    private fun handleIsValidEmail(isValid: Boolean) {
        if (isValid) {
            println("Email valido")
        } else {
            binding.emailInputLayout.error = "Ingresa un email valido"
            println("Email invalido")
        }
    }

    private fun handleLogin(result: Result<Unit>) {
        if (result.isSuccess) {
            println("Hemos hecho login")
        } else {
            println("Fallo el login")
        }
    }

    private fun initListeners() = with(binding) {
        loginButton.setOnClickListener {
            validateEmail()
        }
    }

    private fun validateEmail() {
        loginViewModel.validateEmail(getEmail())
    }

    private fun setUpObservers() {
        loginViewModel.loginData.observe(this, ::handleLogin)

        loginViewModel.emailValidateData.observe(this, ::handleIsValidEmail)
    }
}