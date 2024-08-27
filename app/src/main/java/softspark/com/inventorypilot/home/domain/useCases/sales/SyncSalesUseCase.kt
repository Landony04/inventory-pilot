package softspark.com.inventorypilot.home.domain.useCases.sales

interface SyncSalesUseCase {
    suspend operator fun invoke()
}