package softspark.com.inventorypilot.common.domain.useCases

import java.util.UUID

class GenerateIdUseCaseImpl : GenerateIdUseCase {

    override fun invoke(): String {
        val uuid = UUID.randomUUID().toString().replace("-", "")
        val timestamp = System.currentTimeMillis().toString()
        return "${uuid}_$timestamp" //
    }
}