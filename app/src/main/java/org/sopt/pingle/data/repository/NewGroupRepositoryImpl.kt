package org.sopt.pingle.data.repository

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.NewGroupRemoteDataSource
import org.sopt.pingle.data.model.remote.request.RequestNewGroupCreateDto
import org.sopt.pingle.domain.model.NewGroupCheckNameEntity
import org.sopt.pingle.domain.model.NewGroupKeywordEntity
import org.sopt.pingle.domain.repository.NewGroupRepository

class NewGroupRepositoryImpl @Inject constructor(
    private val newGroupRemoteDataSource: NewGroupRemoteDataSource
) : NewGroupRepository {
    override suspend fun getNewGroupCheckName(teamName: String): Result<NewGroupCheckNameEntity> =
        runCatching {
            newGroupRemoteDataSource.getNewGroupCheckName(teamName).data.toNewGroupCheckNameEntity()
        }

    override suspend fun getNewGroupKeywords(): Result<List<NewGroupKeywordEntity>> = runCatching {
        newGroupRemoteDataSource.getNewGroupKeyword().data.map { keywords ->
            keywords.toNewGroupKeywordsEntity()
        }
    }

    override suspend fun postNewGroupCreate(requestNewGroupCreateDto: RequestNewGroupCreateDto) =
        runCatching {
            newGroupRemoteDataSource.postNewGroupCreate(requestNewGroupCreateDto = requestNewGroupCreateDto).data.toNewGroupCreateEntity()
        }
}
