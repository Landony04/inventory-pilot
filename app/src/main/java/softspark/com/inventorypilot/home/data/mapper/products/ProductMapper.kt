package softspark.com.inventorypilot.home.data.mapper.products

import softspark.com.inventorypilot.home.data.local.entity.products.ProductCategoryEntity
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
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