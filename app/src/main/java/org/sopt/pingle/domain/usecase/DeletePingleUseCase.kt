package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.MyPingleEntity
import org.sopt.pingle.domain.repository.PingleRepository

class DeletePingleUseCase(
    private val pingleRepository: PingleRepository
) {
    suspend operator fun invoke(
        meetingId: Long
    ): Flow<Unit?> =
        pingleRepository.deletePingle(meetingId = meetingId)
}
