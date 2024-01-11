package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupInfoDto
import org.sopt.pingle.util.base.BaseResponse

interface JoinGroupCodeRemoteDataSource {
    suspend fun getJoinGroupInfo(teamId: Int): BaseResponse<ResponseJoinGroupInfoDto>
}