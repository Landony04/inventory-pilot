package softspark.com.inventorypilot.home.domain.repositories.products

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.local.dao.products.ProductDao
import softspark.com.inventorypilot.home.data.mapper.products.toProductDomain
import softspark.com.inventorypilot.home.data.mapper.products.toProductEntity
import softspark.com.inventorypilot.home.data.mapper.products.toProductListDomain
import softspark.com.inventorypilot.home.data.repositories.ProductsRepository
import softspark.com.inventorypilot.home.domain.models.products.Product
import softspark.com.inventorypilot.home.remote.ProductsApi
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val productsApi: ProductsApi,
    private val productDao: ProductDao
) : ProductsRepository {
    override suspend fun getAllProducts(): Flow<Result<ArrayList<Product>>> =
        flow<Result<ArrayList<Product>>> {

            val apiResult = productsApi.getAllProducts().toProductListDomain()

            insertProducts(apiResult)

            val localResult =
                productDao.getAllProducts().map { productEntity -> productEntity.toProductDomain() }

            emit(Result.Success(data = ArrayList(localResult)))

        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun insertProducts(products: List<Product>) = withContext(dispatchers.io()) {
        productDao.insertProducts(products.map { product -> async { product.toProductEntity() }.await() })
    }
}
