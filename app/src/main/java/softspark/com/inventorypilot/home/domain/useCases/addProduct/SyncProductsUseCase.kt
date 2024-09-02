package softspark.com.inventorypilot.home.domain.useCases.addProduct

interface SyncProductsUseCase {
    suspend operator fun invoke()
}