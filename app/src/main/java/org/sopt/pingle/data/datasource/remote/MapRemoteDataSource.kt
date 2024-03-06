package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponsePinDto
import org.sopt.pingle.data.model.remote.response.ResponsePingleDto
import org.sopt.pingle.util.base.BaseResponse

interface MapRemoteDataSource {
    suspend fun getPinListWithoutFiltering(
        teamId: Long,
        category: String?,
        searchWord: String?
    ): BaseResponse<List<ResponsePinDto>>

    suspend fun getMapPingleList(teamId: Long, pinId: Long, category: String?): BaseResponse<List<ResponsePingleDto>>
}
