package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponsePinListDto
import org.sopt.pingle.util.base.BaseResponse

interface MapRemoteDataSource {
    suspend fun getPinListWithoutFiltering(teamId: Long, category: String?): BaseResponse<List<ResponsePinListDto>>
}