package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PlanMeetingEntity

interface PlanMeetingRepository {
    suspend fun postPlanMeeting(
        teamId: Int,
        planMeetingEntity: PlanMeetingEntity
    ): Flow<Unit?>
}
