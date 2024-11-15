package softspark.com.inventorypilot.home.data.mapper.products

import softspark.com.inventorypilot.home.data.local.entity.products.CategoryProductSyncEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductCategoryEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductEntity
import softspark.com.inventorypilot.home.data.local.entity.products.ProductSyncEntity
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.domain.models.sales.CartItem
import softspark.com.inventorypilot.home.domain.models.sales.ProductSale
import softspark.com.inventorypilot.home.remote.dto.categoryProduct.AddCategoryRequest
import softspark.com.inventorypilot.home.remote.dto.products.AddProductRequest
import softspark.com.inventorypilot.home.remote.dto.products.GetProductsResponse
import softspark.com.inventorypilot.home.remote.dto.products.ProductCategoryDto
import softspark.com.inventorypilot.home.remote.dto.products.ProductCategoryResponse
import softspark.com.inventorypilot.home.remote.dto.products.UpdateProductDto

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

fun ProductCategory.toCategorySyncEntity(): CategoryProductSyncEntity = CategoryProductSyncEntity(
    id = id
)

fun ProductCategory.toCategoryProductToDto(): ProductCategoryDto = ProductCategoryDto(
    name = name
)

fun ProductCategory.toAddCategoryProductRequest(id: String): AddCategoryRequest {
    return mapOf(id to this.toCategoryProductToDto())
}

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
            priceCost = dto.priceCost,
            stock = dto.stock,
            addDate = dto.addDate,
            createBy = dto.createBy
        )
    }
}

fun Product.toProductEntity(): ProductEntity = ProductEntity(
    productId = id,
    categoryId = categoryId,
    name = name,
    description = description,
    price = price,
    priceCost = priceCost,
    stock = stock,
    addDate = addDate,
    createBy = createBy
)

fun ProductEntity.toProductDomain(): Product = Product(
    id = productId,
    categoryId = categoryId,
    name = name,
    description = description,
    price = price,
    priceCost = priceCost,
    stock = stock,
    addDate = addDate,
    createBy = createBy
)

fun ProductEntity.toProductSale(quantity: Int): ProductSale = ProductSale(
    id = productId,
    quantity = quantity,
    price = price,
    priceCost = priceCost,
    name = name
)

fun Product.toCartItem(quantity: Int): CartItem = CartItem(
    productId = id,
    quantity = quantity,
    price = price,
    priceCost = priceCost,
    productName = name
)

fun Product.toProductSyncEntity(): ProductSyncEntity = ProductSyncEntity(id = id)

fun Product.toProductToDto(): UpdateProductDto = UpdateProductDto(
    categoryId = categoryId,
    name = name,
    description = description,
    price = price,
    stock = stock,
    addDate = addDate,
    createBy = createBy
)

fun Product.toAddProductRequest(id: String): AddProductRequest {
    return mapOf(id to this.toProductToDto())
}

fun ProductEntity.toProductSyncEntity(): ProductSyncEntity = ProductSyncEntity(
    id = productId
)
