package softspark.com.inventorypilot.login.data.matcher

import android.util.Patterns
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import softspark.com.inventorypilot.login.domain.matcher.EmailMatcher

class EmailMatcherImpl : EmailMatcher {
    override fun isValid(email: String): Flow<Boolean> {
        return flow {
            emit(Patterns.EMAIL_ADDRESS.matcher(email).matches())
        }
    }
}