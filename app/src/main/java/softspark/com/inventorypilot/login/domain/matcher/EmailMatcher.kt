package softspark.com.inventorypilot.login.domain.matcher

import kotlinx.coroutines.flow.Flow

interface EmailMatcher {
    fun isValid(email: String): Flow<Boolean>
}