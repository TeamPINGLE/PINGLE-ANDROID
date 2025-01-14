package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.PlanLocationEntity
import org.sopt.pingle.domain.model.PlanMeetingEntity

interface PlanRepository {
    suspend fun getPlanLocationList(searchWord: String): Flow<List<PlanLocationEntity>>

    suspend fun postPlanMeeting(
        teamId: Int,
        planMeetingEntity: PlanMeetingEntity
    ): Flow<Unit?>
}
