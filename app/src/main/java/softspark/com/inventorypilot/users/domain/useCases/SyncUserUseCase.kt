package softspark.com.inventorypilot.users.domain.useCases

interface SyncUserUseCase {
    suspend operator fun invoke()
}