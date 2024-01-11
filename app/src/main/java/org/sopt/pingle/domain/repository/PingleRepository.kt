package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow

interface PingleRepository {
    suspend fun postPingleJoin(meetingId: Long): Flow<Unit?>
    suspend fun postPingleCancel(meetingId: Long): Flow<Unit?>
}
