package softspark.com.inventorypilot.home.remote

import retrofit2.http.GET
import retrofit2.http.PATCH
import softspark.com.inventorypilot.home.remote.dto.products.ProductCategoryDto
import softspark.com.inventorypilot.home.remote.dto.products.ProductCategoryResponse

interface ProductsApi {

    companion object {
        const val BASE_URL = "https://inventory-pilot-default-rtdb.firebaseio.com/"
    }

    @GET("businesses/business_info_id_1/categories.json")
    suspend fun getProductCategories(): ProductCategoryResponse

    @PATCH("businesses/business_info_id_1/categories.json")
    suspend fun insertCategory(productCategoryDto: ProductCategoryDto)
}
