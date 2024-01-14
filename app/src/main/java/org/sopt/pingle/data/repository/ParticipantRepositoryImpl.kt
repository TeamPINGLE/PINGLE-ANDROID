package org.sopt.pingle.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.ParticipantRemoteDataSource
import org.sopt.pingle.domain.model.ParticipantEntity
import org.sopt.pingle.domain.repository.ParticipantRepository
import javax.inject.Inject

class ParticipantRepositoryImpl @Inject constructor(
    private val participantRemoteDataSource: ParticipantRemoteDataSource
) : ParticipantRepository {
    override fun getParticipantList(meetingId: Long): Flow<ParticipantEntity> = flow {
        val result = runCatching {
            participantRemoteDataSource.getParticipantList(meetingId = meetingId).data.toParticipantEntity()
        }
        emit(result.getOrThrow())
    }
}