package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.ParticipantEntity

interface ParticipantRepository {
    suspend fun getParticipantList(meetingId: Long): Flow<ParticipantEntity>
}
