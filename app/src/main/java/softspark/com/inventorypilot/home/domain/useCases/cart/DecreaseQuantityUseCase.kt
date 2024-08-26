package softspark.com.inventorypilot.home.domain.useCases.cart

interface DecreaseQuantityUseCase {
    suspend operator fun invoke(cartItemId: String)
}