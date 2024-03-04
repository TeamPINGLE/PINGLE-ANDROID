package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.MapRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponsePinListDto
import org.sopt.pingle.data.model.remote.response.ResponsePingleListDto
import org.sopt.pingle.data.service.MapService
import org.sopt.pingle.util.base.BaseResponse

class MapRemoteDataSourceImpl @Inject constructor(
    private val mapService: MapService
) : MapRemoteDataSource {
    override suspend fun getPinListWithoutFiltering(
        teamId: Long,
        category: String?,
        searchWord: String?
    ): BaseResponse<List<ResponsePinListDto>> =
        mapService.getPinListWithoutFiltering(
            teamId = teamId,
            category = category,
            searchWord = searchWord
        )

    override suspend fun getPingleList(
        teamId: Long,
        pinId: Long,
        category: String?
    ): BaseResponse<List<ResponsePingleListDto>> =
        mapService.getPingleList(teamId = teamId, pinId = pinId, category = category)
}
