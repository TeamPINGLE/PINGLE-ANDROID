package org.sopt.pingle.domain.repository

import org.sopt.pingle.domain.model.PingleEntity

interface MainListRepository {
    suspend fun getMainListPingleList(
        searchWord: String?,
        category: String?,
        teamId: Long,
        order: String
    ): Result<List<PingleEntity>>
}