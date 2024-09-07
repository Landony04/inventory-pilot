package softspark.com.inventorypilot.home.domain.repositories.session

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import softspark.com.inventorypilot.common.data.local.InventoryPilotDatabase
import softspark.com.inventorypilot.common.data.util.DispatcherProvider
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.home.data.repositories.SessionRepository
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dispatcherProvider: DispatcherProvider,
    private val firebaseAuth: FirebaseAuth,
    private val inventoryPilotDatabase: InventoryPilotDatabase
) : SessionRepository {
    override suspend fun logout(): Flow<Result<Boolean>> = flow {
        try {
            firebaseAuth.signOut()
            inventoryPilotDatabase.clearAllTables()
            emit(Result.Success(data = true))
        } catch (exception: Exception) {
            emit(Result.Error(exception))
        }
    }.flowOn(dispatcherProvider.io())
}
