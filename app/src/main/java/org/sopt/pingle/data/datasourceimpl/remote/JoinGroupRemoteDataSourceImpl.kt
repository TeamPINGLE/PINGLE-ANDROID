package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.JoinGroupRemoteDataSource
import org.sopt.pingle.data.model.remote.request.RequestJoinGroupCodeDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupCodeDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupInfoDto
import org.sopt.pingle.data.model.remote.response.ResponseJoinGroupSearchDto
import org.sopt.pingle.data.service.JoinGroupService
import org.sopt.pingle.util.base.BaseResponse

class JoinGroupRemoteDataSourceImpl @Inject constructor(
    private val joinGroupService: JoinGroupService
) : JoinGroupRemoteDataSource {
    override suspend fun getJoinGroupSearch(teamName: String): BaseResponse<List<ResponseJoinGroupSearchDto>> =
        joinGroupService.getJoinGroupSearch(teamName = teamName)

    override suspend fun getJoinGroupInfo(teamId: Int): BaseResponse<ResponseJoinGroupInfoDto> =
        joinGroupService.getJoinGroupInfo(teamId = teamId)

    override suspend fun postJoinGroupCode(
        teamId: Int,
        requestJoinGroupCodeDto: RequestJoinGroupCodeDto
    ): BaseResponse<ResponseJoinGroupCodeDto> =
        joinGroupService.postJoinGroupCode(
            teamId = teamId,
            requestJoinGroupCodeDto = requestJoinGroupCodeDto
        )
}
