package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PlanMeetingEntity
import org.sopt.pingle.domain.repository.PlanRepository

class PostPlanMeetingUseCase(
    private val planRepository: PlanRepository
) {
    suspend operator fun invoke(teamId: Int, planMeetingEntity: PlanMeetingEntity): Flow<Unit?> =
        planRepository.postPlanMeeting(
            teamId = teamId,
            planMeetingEntity = planMeetingEntity
        )
}
