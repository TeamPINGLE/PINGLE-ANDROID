package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.MapRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponsePinDto
import org.sopt.pingle.data.model.remote.response.ResponsePingleDto
import org.sopt.pingle.data.service.MapService
import org.sopt.pingle.util.base.BaseResponse

class MapRemoteDataSourceImpl @Inject constructor(
    private val mapService: MapService
) : MapRemoteDataSource {
    override suspend fun getPinListWithoutFiltering(
        teamId: Long,
        category: String?,
        searchWord: String?
    ): BaseResponse<List<ResponsePinDto>> =
        mapService.getPinListWithoutFiltering(
            teamId = teamId,
            category = category,
            searchWord = searchWord
        )

    override suspend fun getMapPingleList(
        teamId: Long,
        pinId: Long,
        category: String?
    ): BaseResponse<List<ResponsePingleDto>> =
        mapService.getMapPingleList(teamId = teamId, pinId = pinId, category = category)
}
