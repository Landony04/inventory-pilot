package softspark.com.inventorypilot.home.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import softspark.com.inventorypilot.home.remote.dto.products.UpdateProductRequest
import softspark.com.inventorypilot.home.remote.dto.sales.GetSalesResponse

interface SalesApi {
    @GET("businesses/business_info_id_1/sales.json")
    suspend fun getAllSales(): GetSalesResponse

    @GET("businesses/business_info_id_1/sales.json")
    suspend fun getSalesForDate(@Query("date") date: String): GetSalesResponse

    @PATCH("businesses/business_info_id_1/sales.json")
    suspend fun insertSale(@Body getSalesResponse: GetSalesResponse)

    @PATCH("businesses/business_info_id_1/products/{id}.json")
    suspend fun updateProductStock(
        @Path("id") productId: String,
        @Body product: UpdateProductRequest
    )
}