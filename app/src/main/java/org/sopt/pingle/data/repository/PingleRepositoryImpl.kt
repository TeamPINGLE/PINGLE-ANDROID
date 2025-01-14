package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.PingleRemoteDataSource
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.repository.PingleRepository

class PingleRepositoryImpl @Inject constructor(
    private val pingleRemoteDataSource: PingleRemoteDataSource
) : PingleRepository {
    override suspend fun postPingleJoin(meetingId: Long): Result<Unit?> =
        runCatching {
            pingleRemoteDataSource.postPingleJoin(meetingId = meetingId).data
        }

    override suspend fun deletePingleCancel(meetingId: Long): Result<Unit?> =
        runCatching {
            pingleRemoteDataSource.deletePingleCancel(meetingId = meetingId).data
        }

    override suspend fun getMyPingleList(
        teamId: Int,
        participation: Boolean
    ): Flow<List<MyPingleEntity>> = flow {
        val result = runCatching {
            pingleRemoteDataSource.getMyPingleList(
                teamId = teamId,
                participation = participation
            ).data.map { myPingle ->
                myPingle.toMyPingleEntity()
            }
        }
        emit(result.getOrThrow())
    }

    override suspend fun deletePingleDelete(meetingId: Long): Flow<Unit?> = flow {
        val result = runCatching {
            pingleRemoteDataSource.deletePingleDelete(
                meetingId = meetingId
            ).data
        }
        emit(result.getOrThrow())
    }
}
