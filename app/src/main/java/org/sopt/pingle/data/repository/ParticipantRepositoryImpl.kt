package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.ParticipantRemoteDataSource
import org.sopt.pingle.domain.model.ParticipantEntity
import org.sopt.pingle.domain.repository.ParticipantRepository

class ParticipantRepositoryImpl @Inject constructor(
    private val participantRemoteDataSource: ParticipantRemoteDataSource
) : ParticipantRepository {
    override suspend fun getParticipantList(meetingId: Long): Flow<ParticipantEntity> = flow {
        val result = runCatching {
            participantRemoteDataSource.getParticipantList(meetingId = meetingId).data.toParticipantEntity()
        }
        emit(result.getOrThrow())
    }
}
