package softspark.com.inventorypilot.home.data.mapper.products

import softspark.com.inventorypilot.home.data.local.entity.products.ProductCategoryEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.remote.dto.products.GetProductsResponse
import softspark.com.inventorypilot.home.remote.dto.products.ProductCategoryResponse

fun ProductCategoryResponse.toDomain(): List<ProductCategory> {
    return entries.map {
        val id = it.key
        val dto = it.value

        ProductCategory(
            id = id,
            name = dto.name
        )
    }
}

fun ProductCategoryEntity.toDomain(): ProductCategory = ProductCategory(
    id = id,
    name = name
)

fun ProductCategory.toEntity(): ProductCategoryEntity = ProductCategoryEntity(
    id = id,
    name = name
)

fun GetProductsResponse.toDomain(): List<Product> {
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

fun Product.toEntity(): ProductEntity = ProductEntity(
    id = id,
    categoryId = categoryId,
    name = name,
    description = description,
    price = price,
    stock = stock
)

fun ProductEntity.toDomain(): Product = Product(
    id = id,
    categoryId = categoryId,
    name = name,
    description = description,
    price = price,
    stock = stock
)