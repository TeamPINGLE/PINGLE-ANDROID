package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.repository.PingleRepository

class PostPingleCancelUseCase(
    private val pingleRepository: PingleRepository
) {
    suspend operator fun invoke(meetingId: Long): Flow<Unit?> =
        pingleRepository.postPingleCancel(meetingId = meetingId)
}
