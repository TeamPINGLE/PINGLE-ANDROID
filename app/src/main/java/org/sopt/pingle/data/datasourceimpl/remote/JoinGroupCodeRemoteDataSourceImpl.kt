package org.sopt.pingle.data.datasourceimpl.remote

import org.sopt.pingle.data.datasource.remote.JoinGroupCodeRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupInfoDto
import org.sopt.pingle.data.service.JoinGroupService
import org.sopt.pingle.util.base.BaseResponse
import javax.inject.Inject

class JoinGroupCodeRemoteDataSourceImpl @Inject constructor(
    private val joinGroupService: JoinGroupService
) : JoinGroupCodeRemoteDataSource {
    override suspend fun getJoinGroupInfo(teamId: Int): BaseResponse<ResponseJoinGroupInfoDto> =
        joinGroupService.getJoinGroupInfo(teamId = teamId)
}