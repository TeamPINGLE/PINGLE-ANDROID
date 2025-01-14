package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.MyPingleEntity

interface PingleRepository {
    suspend fun postPingleJoin(meetingId: Long): Result<Unit?>
    suspend fun deletePingleCancel(meetingId: Long): Result<Unit?>
    suspend fun getMyPingleList(
        teamId: Int,
        participation: Boolean
    ): Flow<List<MyPingleEntity>>

    suspend fun deletePingleDelete(meetingId: Long): Flow<Unit?>
}
