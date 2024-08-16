package softspark.com.inventorypilot.home.domain.repositories.products

import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.local.dao.products.ProductCategoryDao
import softspark.com.inventorypilot.home.data.mapper.products.toCategoryDomain
import softspark.com.inventorypilot.home.data.mapper.products.toCategoryEntity
import softspark.com.inventorypilot.home.data.mapper.products.toCategoryListDomain
import softspark.com.inventorypilot.home.data.repositories.ProductCategoriesRepository
import softspark.com.inventorypilot.home.domain.models.products.ProductCategory
import softspark.com.inventorypilot.home.remote.ProductsApi
import javax.inject.Inject

class ProductCategoriesRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider,
    private val productsApi: ProductsApi,
    private val productCategoryDao: ProductCategoryDao
) : ProductCategoriesRepository {
    override suspend fun getAllCategories(): Flow<Result<ArrayList<ProductCategory>>> =
        flow<Result<ArrayList<ProductCategory>>> {

            val apiResult = productsApi.getProductCategories().toCategoryListDomain()

            insertProductCategories(apiResult)

            val localResult =
                productCategoryDao.getProductCategories()
                    .map { category -> category.toCategoryDomain() }

            emit(Result.Success(data = ArrayList(localResult)))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun getCategoryById(id: String): Flow<Result<ProductCategory>> =
        flow<Result<ProductCategory>> {
            emit(Result.Success(ProductCategory("", "")))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun insertProductCategory(productCategory: ProductCategory): Flow<Result<Boolean>> =
        flow<Result<Boolean>> {
            emit(Result.Success(true))
        }.catch {
            emit(Result.Error(it))
        }.flowOn(dispatchers.io())

    override suspend fun insertProductCategories(productCategories: List<ProductCategory>) =
        withContext(dispatchers.io()) {
            productCategoryDao.insertCategories(productCategories.map { productCategory -> async { productCategory.toCategoryEntity() }.await() })
        }
}
