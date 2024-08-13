package softspark.com.inventorypilot.splash.domain.repositories

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import softspark.com.inventorypilot.splash.data.repositories.MainRepository

class MainRepositoryImpl : MainRepository {
    override fun getUserId(): Flow<String?> = flow {
        emit(Firebase.auth.currentUser?.uid)
    }
}
