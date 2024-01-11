package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow

interface PingleRepository {
    suspend fun postPingleParticipation(meetingId: Long): Flow<Unit?>
}
