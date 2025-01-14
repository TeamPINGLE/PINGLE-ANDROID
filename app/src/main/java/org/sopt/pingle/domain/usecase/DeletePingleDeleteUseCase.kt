package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.repository.PingleRepository

class DeletePingleDeleteUseCase(
    private val pingleRepository: PingleRepository
) {
    suspend operator fun invoke(
        meetingId: Long
    ): Flow<Unit?> =
        pingleRepository.deletePingleDelete(meetingId = meetingId)
}
