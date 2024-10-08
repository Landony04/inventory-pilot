package softspark.com.inventorypilot.home.domain.useCases.cart

interface IncreaseQuantityUseCase {
    suspend operator fun invoke(cartItemId: String)
}