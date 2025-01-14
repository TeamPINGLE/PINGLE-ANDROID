package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.RankingRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponseRankingDto
import org.sopt.pingle.data.service.RankingService
import org.sopt.pingle.util.base.BaseResponse

class RankingRemoteDataSourceImpl @Inject constructor(
    private val rankingService: RankingService
) : RankingRemoteDataSource {
    override suspend fun getRanking(teamId: Long): BaseResponse<ResponseRankingDto> =
        rankingService.getRanking(teamId = teamId)
}
