package softspark.com.inventorypilot.home.data.mapper.cart

import softspark.com.inventorypilot.home.data.local.entity.cart.CartItemEntity
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import softspark.com.inventorypilot.home.domain.models.sales.ProductSale

fun CartItem.toCartItemEntity(): CartItemEntity = CartItemEntity(
    cartItemId = cartItemId,
    productId = productId,
    quantity = quantity,
    price = price,
    totalAmount = totalAmount ?: price,
    productName = productName
)

fun CartItemEntity.toCartItem(): CartItem = CartItem(
    cartItemId = cartItemId,
    productId = productId,
    quantity = quantity,
    price = price,
    productName = productName,
    totalAmount = totalAmount
)

fun CartItem.toProductSale(): ProductSale = ProductSale(
    id = productId,
    price = price,
    quantity = quantity
)

