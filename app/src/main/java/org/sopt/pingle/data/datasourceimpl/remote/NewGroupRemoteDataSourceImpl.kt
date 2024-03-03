package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.NewGroupRemoteDataSource
import org.sopt.pingle.data.model.remote.request.RequestNewGroupCreateDto
import org.sopt.pingle.data.model.remote.response.ResponseNewGroupCheckNameDto
import org.sopt.pingle.data.model.remote.response.ResponseNewGroupCreateDto
import org.sopt.pingle.data.model.remote.response.ResponseNewGroupKeywordsDto
import org.sopt.pingle.data.service.NewGroupService
import org.sopt.pingle.util.base.BaseResponse

class NewGroupRemoteDataSourceImpl @Inject constructor(
    private val newGroupService: NewGroupService
) : NewGroupRemoteDataSource {
    override suspend fun getNewGroupCheckName(teamName: String): BaseResponse<ResponseNewGroupCheckNameDto> =
        newGroupService.getNewGroupCheckName(teamName)

    override suspend fun getNewGroupKeyword(): BaseResponse<List<ResponseNewGroupKeywordsDto>> =
        newGroupService.getNewGroupKeywords()

    override suspend fun postNewGroupCreate(
        requestNewGroupCreateDto: RequestNewGroupCreateDto
    ): BaseResponse<ResponseNewGroupCreateDto> =
        newGroupService.postNewGroupCreate(requestNewGroupCreateDto = requestNewGroupCreateDto)
}
