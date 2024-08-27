package softspark.com.inventorypilot.home.data.mapper.sales

import softspark.com.inventorypilot.common.data.extension.formatDateUTCWithoutHours
import softspark.com.inventorypilot.common.data.extension.formatUtcToReadableDate
import softspark.com.inventorypilot.home.data.local.entity.products.SaleProductsList
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleEntity
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleSyncEntity
import softspark.com.inventorypilot.home.domain.models.sales.ProductSale
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.remote.dto.sales.GetSalesResponse
import softspark.com.inventorypilot.home.remote.dto.sales.ProductSaleDto
import softspark.com.inventorypilot.home.remote.dto.sales.ProductsSaleResponse
import softspark.com.inventorypilot.home.remote.dto.sales.SaleDto

fun GetSalesResponse.toSaleListDomain(): List<Sale> {
    return entries.map {
        val id = it.key
        val dto = it.value

        Sale(
            id = id,
            clientId = dto.clientId,
            date = dto.date,
            totalAmount = dto.totalAmount,
            userId = dto.userId,
            products = ArrayList(dto.products.toProductSaleDomain()),
            status = dto.status
        )
    }
}

fun Sale.toSaleEntity(): SaleEntity = SaleEntity(
    saleId = id,
    clientId = clientId,
    date = date,
    totalAmount = totalAmount,
    userOwnerId = userId,
    products = SaleProductsList(products),
    dateWithoutHours = date.formatDateUTCWithoutHours(),
    status = status
)

fun SaleEntity.toSaleDomain(): Sale = Sale(
    id = saleId,
    clientId = clientId,
    date = date,
    totalAmount = totalAmount,
    userId = userOwnerId,
    products = ArrayList(products.products),
    status = status
)

fun ProductsSaleResponse.toProductSaleDomain(): List<ProductSale> {
    return entries.map {
        val id = it.key
        val dto = it.value

        ProductSale(
            id = id,
            price = dto.price,
            quantity = dto.quantity
        )
    }
}

fun Sale.toSyncEntity(): SaleSyncEntity = SaleSyncEntity(id = id)

fun Sale.toSaleRequestDto(): GetSalesResponse {
    val dto = SaleDto(
        date = date,
        totalAmount = totalAmount,
        userId = userId,
        products = toProductMap(ArrayList(products.map { it.toProductSaleDto(it.id) })),
        status = "completed",
    )

    return mapOf(id to dto)
}

fun toProductMap(products: ArrayList<ProductSaleDto>): ProductsSaleResponse {
    return products.associateBy { it.id.toString() }
}

fun ProductSale.toProductSaleDto(id: String): ProductSaleDto = ProductSaleDto(
    id = id,
    price = price,
    quantity = quantity
)