package softspark.com.inventorypilot.home.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import softspark.com.inventorypilot.home.remote.dto.categoryProduct.AddCategoryRequest
import softspark.com.inventorypilot.home.remote.dto.products.AddProductRequest
import softspark.com.inventorypilot.home.remote.dto.products.GetProductsResponse
import softspark.com.inventorypilot.home.remote.dto.products.ProductCategoryResponse

interface ProductsApi {

    @GET("businesses/branches/{branchId}/categories.json")
    suspend fun getProductCategories(@Path("branchId") branchId: String): ProductCategoryResponse

    @PATCH("businesses/branches/{branchId}/categories.json")
    suspend fun insertCategory(
        @Path("branchId") branchId: String,
        @Body addCategoryRequest: AddCategoryRequest
    )

    @GET("businesses/branches/{branchId}/products.json")
    suspend fun getAllProducts(@Path("branchId") branchId: String): GetProductsResponse

    @PATCH("businesses/branches/{branchId}/products.json")
    suspend fun insertOrUpdateProduct(
        @Path("branchId") branchId: String,
        @Body addProductRequest: AddProductRequest
    )
}
