package softspark.com.inventorypilot.home.data.mapper.products

import softspark.com.inventorypilot.home.data.local.entity.products.ProductCategoryEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import softspark.com.inventorypilot.home.remote.dto.products.GetProductsResponse
import softspark.com.inventorypilot.home.remote.dto.products.ProductCategoryResponse

fun ProductCategoryResponse.toCategoryListDomain(): List<ProductCategory> {
    return entries.map {
        val id = it.key
        val dto = it.value

        ProductCategory(
            id = id,
            name = dto.name
        )
    }
}

fun ProductCategoryEntity.toCategoryDomain(): ProductCategory = ProductCategory(
    id = categoryId,
    name = name
)

fun ProductCategory.toCategoryEntity(): ProductCategoryEntity = ProductCategoryEntity(
    categoryId = id,
    name = name
)

fun GetProductsResponse.toProductListDomain(): List<Product> {
    return entries.map {
        val id = it.key
        val dto = it.value

        Product(
            id = id,
            categoryId = dto.categoryId,
            name = dto.name,
            description = dto.description,
            price = dto.price,
            stock = dto.stock
        )
    }
}

fun Product.toProductEntity(): ProductEntity = ProductEntity(
    productId = id,
    categoryId = categoryId,
    name = name,
    description = description,
    price = price,
    stock = stock
)

fun ProductEntity.toProductDomain(): Product = Product(
    id = productId,
    categoryId = categoryId,
    name = name,
    description = description,
    price = price,
    stock = stock
)

fun Product.toCartItem(quantity: Int): CartItem = CartItem(
    productId = id,
    quantity = quantity,
    price = price,
    productName = name,
    totalPrice = (price * quantity)
)
