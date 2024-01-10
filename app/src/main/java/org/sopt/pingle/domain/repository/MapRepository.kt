package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PinEntity

interface MapRepository {
    suspend fun getPinListWithoutFiltering(teamId: Long, category: String?): Flow<List<PinEntity>>
}
