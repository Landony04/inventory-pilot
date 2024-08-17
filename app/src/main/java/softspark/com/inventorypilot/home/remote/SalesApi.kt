package softspark.com.inventorypilot.home.remote

import retrofit2.http.GET
import softspark.com.inventorypilot.home.remote.dto.sales.GetSalesResponse

interface SalesApi {
    @GET("businesses/business_info_id_1/sales.json")
    suspend fun getAllSales(): GetSalesResponse
}