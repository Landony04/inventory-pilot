package softspark.com.inventorypilot.home.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import softspark.com.inventorypilot.home.remote.dto.sales.GetSalesResponse

interface SalesApi {
    @GET("businesses/business_info_id_1/sales.json")
    suspend fun getAllSales(): GetSalesResponse

    @PATCH("businesses/business_info_id_1/sales.json")
    suspend fun insertSale(@Body getSalesResponse: GetSalesResponse)
}