package softspark.com.inventorypilot.home.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query
import softspark.com.inventorypilot.home.remote.dto.products.UpdateProductRequest
import softspark.com.inventorypilot.home.remote.dto.sales.GetSalesResponse

interface SalesApi {

    @GET("businesses/branches/{branchId}/sales.json")
    suspend fun getSalesForDate(
        @Path("branchId") branchId: String,
        @Query("date") date: String
    ): GetSalesResponse

    @PATCH("businesses/branches/{branchId}/sales.json")
    suspend fun insertSale(
        @Path("branchId") branchId: String,
        @Body getSalesResponse: GetSalesResponse
    )

    @PATCH("businesses/branches/{branchId}/products/{id}.json")
    suspend fun updateProductStock(
        @Path("branchId") branchId: String,
        @Path("id") productId: String,
        @Body product: UpdateProductRequest
    )
}