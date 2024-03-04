package org.sopt.pingle.domain.repository

import org.sopt.pingle.domain.model.RankingEntity

interface RankingRepository {
    suspend fun getRanking(teamId: Long): Result<RankingEntity>
}
