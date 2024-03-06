package org.sopt.pingle.data.repository

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.MainListRemoteDataSource
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.repository.MainListRepository

class MainListRepositoryImpl @Inject constructor(
    private val mainListRemoteDataSource: MainListRemoteDataSource
) : MainListRepository {
    override suspend fun getMainListPingleList(
        searchWord: String?,
        category: String?,
        teamId: Long,
        order: String
    ): Result<List<PingleEntity>> = runCatching {
        mainListRemoteDataSource.getMainListPingleList(
            searchWord = searchWord,
            category = category,
            teamId = teamId,
            order = order
        ).data.meetings.map { meeting -> meeting.toPingleEntity() }
    }
}
