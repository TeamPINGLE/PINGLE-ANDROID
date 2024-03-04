package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity

interface MapRepository {
    suspend fun getPinListWithoutFiltering(teamId: Long, category: String?, searchWord: String?): Flow<List<PinEntity>>
    suspend fun getPingleList(teamId: Long, pinId: Long, category: String?): Flow<List<PingleEntity>>
}
