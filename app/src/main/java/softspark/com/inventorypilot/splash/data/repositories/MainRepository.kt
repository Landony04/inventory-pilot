package softspark.com.inventorypilot.splash.data.repositories

import kotlinx.coroutines.flow.Flow

interface MainRepository {
    fun getUserId(): Flow<String?>
}