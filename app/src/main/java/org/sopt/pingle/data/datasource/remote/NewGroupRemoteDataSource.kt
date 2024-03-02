package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponseNewGroupCheckNameDto
import org.sopt.pingle.data.model.remote.response.ResponseNewGroupKeywordsDto
import org.sopt.pingle.util.base.BaseResponse

interface NewGroupRemoteDataSource {
    suspend fun getNewGroupCheckName(teamName: String): BaseResponse<ResponseNewGroupCheckNameDto>

    suspend fun getNewGroupKeyword(): BaseResponse<List<ResponseNewGroupKeywordsDto>>
}
