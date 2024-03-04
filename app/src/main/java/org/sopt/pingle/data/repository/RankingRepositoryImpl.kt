package org.sopt.pingle.data.repository

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.RankingRemoteDataSource
import org.sopt.pingle.domain.model.RankingEntity
import org.sopt.pingle.domain.repository.RankingRepository

class RankingRepositoryImpl @Inject constructor(
    private val rankingRemoteDataSource: RankingRemoteDataSource
) : RankingRepository {
    override suspend fun getRanking(teamId: Long): Result<RankingEntity> = runCatching {
        rankingRemoteDataSource.getRanking(teamId = teamId).data.toRankingEntity()
    }
}
