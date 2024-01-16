package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.PingleRemoteDataSource
import org.sopt.pingle.domain.repository.PingleRepository

class PingleRepositoryImpl @Inject constructor(
    private val pingleRemoteDataSource: PingleRemoteDataSource
) : PingleRepository {
    override suspend fun postPingleJoin(meetingId: Long): Flow<Unit?> = flow {
        val result = runCatching {
            pingleRemoteDataSource.postPingleJoin(meetingId = meetingId).data
        }
        emit(result.getOrThrow())
    }

    override suspend fun deletePingleCancel(meetingId: Long): Flow<Unit?> = flow {
        val result = runCatching {
            pingleRemoteDataSource.deletePingleCancel(meetingId = meetingId).data
        }
        emit(result.getOrThrow())
    }
}
