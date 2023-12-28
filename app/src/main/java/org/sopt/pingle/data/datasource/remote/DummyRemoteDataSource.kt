package org.sopt.pingle.data.datasource.remote

import org.sopt.pingle.data.model.remote.response.ResponseGetDummyUserListDto

interface DummyRemoteDataSource {
    suspend fun getDummyUserList(page: Int): ResponseGetDummyUserListDto
}
