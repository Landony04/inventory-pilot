package softspark.com.inventorypilot.home.data.mapper.sales

import softspark.com.inventorypilot.home.data.local.entity.products.SaleProductsList
import softspark.com.inventorypilot.home.data.local.entity.sales.SaleEntity
import softspark.com.inventorypilot.home.domain.models.sales.ProductSale
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.remote.dto.sales.GetSalesResponse
import softspark.com.inventorypilot.home.remote.dto.sales.ProductsSaleResponse

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
            products = ArrayList(dto.products.toProductSaleDomain())
        )
    }
}

fun Sale.toSaleEntity(): SaleEntity = SaleEntity(
    saleId = id,
    clientId = clientId,
    date = date,
    totalAmount = totalAmount,
    userOwnerId = userId,
    products = SaleProductsList(products)
)

fun SaleEntity.toSaleDomain(): Sale = Sale(
    id = saleId,
    clientId = clientId,
    date = date,
    totalAmount = totalAmount,
    userId = userOwnerId,
    products = ArrayList(products.products)
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