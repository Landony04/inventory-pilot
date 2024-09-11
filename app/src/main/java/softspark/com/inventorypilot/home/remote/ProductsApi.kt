package softspark.com.inventorypilot.home.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import softspark.com.inventorypilot.home.remote.dto.categoryProduct.AddCategoryRequest
import softspark.com.inventorypilot.home.remote.dto.products.AddProductRequest
import softspark.com.inventorypilot.home.remote.dto.products.GetProductsResponse
import softspark.com.inventorypilot.home.remote.dto.products.ProductCategoryResponse

interface ProductsApi {

    @GET("businesses/business_info_id_1/categories.json")
    suspend fun getProductCategories(): ProductCategoryResponse

    @PATCH("businesses/business_info_id_1/categories.json")
    suspend fun insertCategory(@Body addCategoryRequest: AddCategoryRequest)

    @GET("businesses/business_info_id_1/products.json")
    suspend fun getAllProducts(): GetProductsResponse

    @PATCH("businesses/business_info_id_1/products.json")
    suspend fun insertOrUpdateProduct(@Body addProductRequest: AddProductRequest)
}
