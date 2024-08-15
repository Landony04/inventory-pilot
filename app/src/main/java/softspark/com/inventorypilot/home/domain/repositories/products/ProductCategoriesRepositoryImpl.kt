package softspark.com.inventorypilot.home.domain.repositories.products

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.mapper.products.toDomain
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.remote.ProductsApi
import javax.inject.Inject

class ProductCategoriesRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val productsApi: ProductsApi
) : ProductCategoriesRepository {
    override suspend fun getAllCategories(): Flow<Result<ArrayList<ProductCategory>>> =
        flow<Result<ArrayList<ProductCategory>>> {
            val categories = productsApi.getProductCategories().toDomain()

            emit(Result.Success(data = categories))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

}
