package softspark.com.inventorypilot.common.domain.useCases

import java.util.UUID

class GenerateIdUseCaseImpl : GenerateIdUseCase {
    override fun invoke(): String = UUID.randomUUID()
        .toString()
}