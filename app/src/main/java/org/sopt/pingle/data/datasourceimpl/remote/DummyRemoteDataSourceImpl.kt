package org.sopt.pingle.data.datasourceimpl.remote

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.DummyRemoteDataSource
import org.sopt.pingle.data.model.remote.response.ResponseGetDummyUserListDto
import org.sopt.pingle.data.service.DummyService

class DummyRemoteDataSourceImpl @Inject constructor(
    private val dummyService: DummyService
) : DummyRemoteDataSource {
    override suspend fun getDummyUserList(page: Int): ResponseGetDummyUserListDto =
        dummyService.getDummyListUserList(page = page)
}
