package softspark.com.inventorypilot.login.domain.useCases.authentication

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.login.domain.matcher.EmailMatcher
import javax.inject.Inject

class ValidateEmailUseCaseImpl @Inject constructor(
    private val emailMatcher: EmailMatcher
) : ValidateEmailUseCase {
    override fun invoke(email: String): Flow<Boolean> {
        return emailMatcher.isValid(email)
    }
}