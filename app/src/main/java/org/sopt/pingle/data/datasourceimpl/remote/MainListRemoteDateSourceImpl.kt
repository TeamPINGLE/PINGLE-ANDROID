package org.sopt.pingle.data.datasourceimpl.remote

import org.sopt.pingle.data.datasource.remote.MainListRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponseMainListDto
import org.sopt.pingle.data.service.MainListService
import org.sopt.pingle.util.base.BaseResponse
import javax.inject.Inject

class MainListRemoteDateSourceImpl @Inject constructor(
    private val mainListService: MainListService
) : MainListRemoteDataSource {
    override suspend fun getMainListPingleList(
        searchWord: String?,
        category: String?,
        teamId: Long,
        order: String
    ): BaseResponse<ResponseMainListDto> = mainListService.getMainListPingleList(
        searchWord = searchWord,
        category = category,
        teamId = teamId,
        order = order
    )
}