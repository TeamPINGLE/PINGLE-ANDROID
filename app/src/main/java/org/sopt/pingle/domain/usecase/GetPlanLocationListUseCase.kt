package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PlanLocationEntity
import org.sopt.pingle.domain.repository.PlanRepository

class GetPlanLocationListUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(searchWord: String): Flow<List<PlanLocationEntity>> =
        planRepository.getPlanLocationList(searchWord = searchWord)
}
