package softspark.com.inventorypilot.splash.domain.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.splash.data.repositories.MainRepository
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val dispatchers: DispatcherProvider
) : MainRepository {
    override fun getUserId(): Flow<String?> = flow {
        emit(Firebase.auth.currentUser?.uid)
    }.catch {
        emit(Result.Error(it).toString())
    }.flowOn(dispatchers.io())
}
