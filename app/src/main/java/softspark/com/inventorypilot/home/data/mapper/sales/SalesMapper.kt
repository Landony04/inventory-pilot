package softspark.com.inventorypilot.home.data.mapper.sales

import softspark.com.inventorypilot.home.data.local.entity.sales.SaleEntity
import softspark.com.inventorypilot.home.data.mapper.products.toProductDomain
import softspark.com.inventorypilot.home.data.mapper.products.toProductEntity
import softspark.com.inventorypilot.home.data.mapper.products.toProductListDomain
import softspark.com.inventorypilot.home.domain.models.sales.Sale
import softspark.com.inventorypilot.home.remote.dto.sales.GetSalesResponse
import softspark.com.inventorypilot.login.data.mapper.toEntity
import softspark.com.inventorypilot.login.data.mapper.toUserProfile
import softspark.com.inventorypilot.login.data.mapper.toUserProfileDomain

fun GetSalesResponse.toSaleListDomain(): List<Sale> {
    return entries.map {
        val id = it.key
        val dto = it.value

        Sale(
            id = id,
            clientId = dto.clientId,
            date = dto.date,
            totalAmount = dto.totalAmount,
            user = dto.user.toUserProfile(),
            products = ArrayList(dto.products.toProductListDomain())
        )
    }
}

fun Sale.toSaleEntity(): SaleEntity = SaleEntity(
    id = id,
    clientId = clientId,
    date = date,
    totalAmount = totalAmount,
    user = user.toEntity(),
    products = products.map { it.toProductEntity() }
)

fun SaleEntity.toSaleDomain(): Sale = Sale(
    id = id,
    clientId = clientId,
    date = date,
    totalAmount = totalAmount,
    user = user.toUserProfileDomain(),
    products = ArrayList(products.map { it.toProductDomain() })
)