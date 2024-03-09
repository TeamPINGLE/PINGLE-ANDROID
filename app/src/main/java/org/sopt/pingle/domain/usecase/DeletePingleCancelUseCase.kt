package org.sopt.pingle.domain.usecase

import org.sopt.pingle.domain.repository.PingleRepository

class DeletePingleCancelUseCase(
    private val pingleRepository: PingleRepository
) {
    suspend operator fun invoke(meetingId: Long): Result<Unit?> =
        pingleRepository.deletePingleCancel(meetingId = meetingId)
}
