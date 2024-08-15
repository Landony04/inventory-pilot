package softspark.com.inventorypilot.home.data.mapper.products

import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.remote.dto.products.ProductCategoryResponse

fun ProductCategoryResponse.toDomain(): ArrayList<ProductCategory> {
    return entries.map {
        val id = it.key
        val dto = it.value

        ProductCategory(
            id = id,
            name = dto.name
        )
    }.toMutableList() as ArrayList
}