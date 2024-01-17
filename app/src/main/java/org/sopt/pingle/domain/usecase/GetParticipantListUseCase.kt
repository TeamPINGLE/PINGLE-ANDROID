package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.ParticipantEntity
import org.sopt.pingle.domain.repository.ParticipantRepository

class GetParticipantListUseCase(
    private val participantRepository: ParticipantRepository
) {
    operator fun invoke(meetingId: Long): Flow<ParticipantEntity> =
        participantRepository.getParticipantList(meetingId = meetingId)
}
