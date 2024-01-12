package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.request.RequestJoinGroupCodeDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupCodeDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupInfoDto
import org.sopt.pingle.util.base.BaseResponse

interface JoinGroupRemoteDataSource {
    suspend fun getJoinGroupInfo(teamId: Int): BaseResponse<ResponseJoinGroupInfoDto>
    suspend fun postJoinGroupCode(
        teamId: Int,
        code: RequestJoinGroupCodeDto
    ): BaseResponse<ResponseJoinGroupCodeDto>
}