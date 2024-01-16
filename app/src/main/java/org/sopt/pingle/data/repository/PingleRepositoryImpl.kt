package org.sopt.pingle.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.PingleRemoteDataSource
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.repository.PingleRepository
import javax.inject.Inject

class PingleRepositoryImpl @Inject constructor(
    private val pingleRemoteDataSource: PingleRemoteDataSource
) : PingleRepository {
    override suspend fun postPingleJoin(meetingId: Long): Flow<Unit?> = flow {
        val result = runCatching {
            pingleRemoteDataSource.postPingleJoin(meetingId = meetingId).data
        }
        emit(result.getOrThrow())
    }

    override suspend fun postPingleCancel(meetingId: Long): Flow<Unit?> = flow {
        val result = runCatching {
            pingleRemoteDataSource.postPingleCancel(meetingId = meetingId).data
        }
        emit(result.getOrThrow())
    }

    override suspend fun getPingleParticipationList(
        teamId: Int,
        participation: Boolean
    ): Flow<List<MyPingleEntity>> = flow {
        val result = runCatching {
            pingleRemoteDataSource.getPingleParticipationList(
                teamId = teamId,
                participation = participation
            ).data.map { myPingle ->
                myPingle.toMyPingleEntity()
            }
        }
        emit(result.getOrThrow())
    }

    override suspend fun deletePingle(meetingId: Long): Flow<Unit?> = flow {
        val result = runCatching {
            pingleRemoteDataSource.deletePingle(
                meetingId = meetingId
            ).data
        }
        emit(result.getOrThrow())
    }
}
