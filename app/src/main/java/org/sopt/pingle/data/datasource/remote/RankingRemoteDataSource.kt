package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponseRankingDto
import org.sopt.pingle.util.base.BaseResponse

interface RankingRemoteDataSource {
    suspend fun getRanking(
        teamId: Long
    ): BaseResponse<ResponseRankingDto>
}
