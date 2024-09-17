package softspark.com.inventorypilot.users.domain.useCases

import kotlinx.coroutines.flow.Flow
import softspark.com.inventorypilot.common.entities.base.Result
import softspark.com.inventorypilot.login.domain.models.Branch

interface GetBranchesFromLocalUseCase {
    suspend operator fun invoke(): Flow<Result<List<Branch>>>
}
