package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.MyGroupListRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponseMyGroupDto
import org.sopt.pingle.data.service.MyGroupListService
import org.sopt.pingle.util.base.BaseResponse

class MyGroupListRemoteDataSourceImpl @Inject constructor(
    private val myGroupListService: MyGroupListService
) : MyGroupListRemoteDataSource {
    override suspend fun getMyGroupList(): BaseResponse<List<ResponseMyGroupDto>> =
        myGroupListService.getMyGroupList()
}
