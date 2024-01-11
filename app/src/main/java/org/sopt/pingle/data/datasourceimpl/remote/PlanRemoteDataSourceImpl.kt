package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.PlanRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponsePlanDto
import org.sopt.pingle.data.service.PlanService
import org.sopt.pingle.util.base.BaseResponse

class PlanRemoteDataSourceImpl @Inject constructor(
    private val planService: PlanService
) : PlanRemoteDataSource {
    override suspend fun getPlanLocation(searchWord: String): BaseResponse<List<ResponsePlanDto>> =
        planService.getPlanLocationList(searchWord = searchWord)
}
