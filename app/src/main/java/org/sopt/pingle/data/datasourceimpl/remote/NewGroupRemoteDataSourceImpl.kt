package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.NewGroupRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponseNewGroupKeywordsDto
import org.sopt.pingle.data.service.NewGroupService
import org.sopt.pingle.util.base.BaseResponse

class NewGroupRemoteDataSourceImpl @Inject constructor(
    private val newGroupService: NewGroupService
) : NewGroupRemoteDataSource {
    override suspend fun getNewGroupKeyword(): BaseResponse<List<ResponseNewGroupKeywordsDto>> =
        newGroupService.getNewGroupKeywords()
}
