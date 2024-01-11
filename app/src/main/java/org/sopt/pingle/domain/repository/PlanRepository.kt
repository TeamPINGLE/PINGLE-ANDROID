package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PlanLocationEntity

interface PlanRepository {
    suspend fun getPlanLocationList(searchWord: String): Flow<List<PlanLocationEntity>>
}
