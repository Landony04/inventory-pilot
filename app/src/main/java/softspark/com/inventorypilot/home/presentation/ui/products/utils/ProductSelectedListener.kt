package softspark.com.inventorypilot.home.presentation.ui.products.utils

import softspark.com.inventorypilot.home.domain.models.products.Product

interface ProductSelectedListener {
    fun addToCartProductSelected(product: Product, position: Int)

    fun editProductSelected(productId: String, position: Int)
}