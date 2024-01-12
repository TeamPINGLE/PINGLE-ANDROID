package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PlanMeetingEntity
import org.sopt.pingle.domain.repository.PlanMeetingRepository

class PostPlanMeetingUseCase(
    private val planMeetingRepository: PlanMeetingRepository
) {
    suspend operator fun invoke(teamId: Int, planMeetingEntity: PlanMeetingEntity): Flow<Unit?> =
        planMeetingRepository.postPlanMeeting(
            teamId = teamId,
            planMeetingEntity = planMeetingEntity
        )
}
