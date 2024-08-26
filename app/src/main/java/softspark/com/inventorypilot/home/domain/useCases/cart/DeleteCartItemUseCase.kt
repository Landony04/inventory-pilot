package softspark.com.inventorypilot.home.domain.useCases.cart

interface DeleteCartItemUseCase {
    suspend operator fun invoke(cartItemId: String)
}