package softspark.com.inventorypilot.login.presentation.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import softspark.com.inventorypilot.R
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.databinding.LoginActivityBinding
import softspark.com.inventorypilot.login.domain.entities.PasswordResult
import softspark.com.inventorypilot.login.domain.models.UserProfile
import softspark.com.inventorypilot.login.presentation.viewModel.LoginViewModel
import softspark.com.inventorypilot.navigation.Navigator
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: LoginActivityBinding

    @Inject
    lateinit var navigator: Navigator

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initListeners()
        setUpObservers()
    }

    private fun doLogin() {
        loginViewModel.login(getEmail(), getPassword())
    }

    private fun getEmail(): String = binding.emailEditText.text.toString()

    private fun getPassword(): String = binding.passwordEditText.text.toString()

    private fun handleIsValidEmail(isValid: Boolean) {
        if (isValid) {
            binding.emailInputLayout.error = null
        } else {
            binding.emailInputLayout.error = getString(R.string.text_error_invalid_email)
        }
    }

    private fun handleIsValidPassword(passwordResult: PasswordResult) {
        when (passwordResult) {
            is PasswordResult.Invalid -> binding.passwordEditText.error =
                passwordResult.errorMessage

            PasswordResult.Valid -> doLogin()
        }
    }

    private fun handleGetUserProfile(result: Result<UserProfile>) {
        when (result) {
            is Result.Error -> println("Mostrar el error en un alert ${result.exception.message}")
            is Result.Success -> navigator.navigateToHome()
            Result.Loading -> println("Mostrar progress")
        }
    }

    private fun handleLogin(result: Result<Unit>) {
        when (result) {
            is Result.Error -> println("Fallo el login")
            is Result.Success -> loginViewModel.getUserProfile(getEmail())
            Result.Loading -> println("Mostrar progress")
        }
    }

    private fun initListeners() = with(binding) {
        loginButton.setOnClickListener {
            validateEmailAndPassword()
        }
    }

    private fun setUpObservers() {
        loginViewModel.userProfileData.observe(this, ::handleGetUserProfile)

        loginViewModel.loginData.observe(this, ::handleLogin)

        loginViewModel.emailValidateData.observe(this, ::handleIsValidEmail)

        loginViewModel.passwordValidateData.observe(this, ::handleIsValidPassword)
    }

    private fun validateEmailAndPassword() {
        loginViewModel.validateEmailAndPassword(getEmail(), getPassword())
    }
}