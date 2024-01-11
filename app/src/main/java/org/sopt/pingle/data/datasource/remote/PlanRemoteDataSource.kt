package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponsePlanDto
import org.sopt.pingle.util.base.BaseResponse

interface PlanRemoteDataSource {
    suspend fun getPlanLocation(searchWord: String): BaseResponse<List<ResponsePlanDto>>
}
