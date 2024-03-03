package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponseMainListDto
import org.sopt.pingle.util.base.BaseResponse

interface MainListRemoteDataSource {
    suspend fun getMainListPingleList(
        searchWord: String?,
        category: String?,
        teamId: Long,
        order: String
    ): BaseResponse<ResponseMainListDto>
}