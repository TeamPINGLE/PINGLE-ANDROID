package org.sopt.pingle.data.repository

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.NewGroupRemoteDataSource
import org.sopt.pingle.domain.model.NewGroupCheckNameEntity
import org.sopt.pingle.domain.model.NewGroupKeywordsEntity
import org.sopt.pingle.domain.repository.NewGroupRepository

class NewGroupRepositoryImpl @Inject constructor(
    private val newGroupRemoteDataSource: NewGroupRemoteDataSource
) : NewGroupRepository {
    override suspend fun getNewGroupCheckName(teamName: String): Result<NewGroupCheckNameEntity> =
        runCatching {
            newGroupRemoteDataSource.getNewGroupCheckName(teamName).data.toNewGroupCheckNameEntity()
        }

    override suspend fun getNewGroupKeywords(): Result<List<NewGroupKeywordsEntity>> = runCatching {
        newGroupRemoteDataSource.getNewGroupKeyword().data.map { keywords ->
            keywords.toNewGroupKeywordsEntity()
        }
    }
}
