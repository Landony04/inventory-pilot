package softspark.com.inventorypilot.home.data.mapper.cart

import softspark.com.inventorypilot.home.data.local.entity.cart.CartItemEntity
import softspark.com.inventorypilot.home.domain.models.sales.CartItem

fun CartItem.toCartItemEntity(): CartItemEntity = CartItemEntity(
    cartItemId = cartItemId,
    productId = productId,
    quantity = quantity,
    price = price,
    totalPrice = (quantity * price),
    productName = productName
)

fun CartItemEntity.toCartItem(): CartItem = CartItem(
    cartItemId = cartItemId,
    productId = productId,
    quantity = quantity,
    price = price,
    productName = productName,
    totalPrice = (quantity * price)
)

