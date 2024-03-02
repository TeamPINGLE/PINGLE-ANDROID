package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponseMyGroupDto
import org.sopt.pingle.util.base.BaseResponse

interface MyGroupListRemoteDataSource {
    suspend fun getMyGroupList(): BaseResponse<List<ResponseMyGroupDto>>
}
