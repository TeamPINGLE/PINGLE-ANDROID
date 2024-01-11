package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.PlanMeetingRemoteDataSource
import org.sopt.pingle.data.mapper.toRequestPlanMeetingDto
import org.sopt.pingle.domain.model.PlanMeetingEntity
import org.sopt.pingle.domain.repository.PlanMeetingRepository

class PlanMeetingRepositoryImpl @Inject constructor(
    private val planMeetingRemoteDataSource: PlanMeetingRemoteDataSource
) : PlanMeetingRepository {
    override suspend fun postPlanMeeting(
        teamId: Int,
        planMeetingEntity: PlanMeetingEntity
    ): Flow<Unit?> = flow {
        val result = runCatching {
            planMeetingRemoteDataSource.postPlanMeeting(
                teamId = teamId,
                requestPlanMeetingDto = planMeetingEntity.toRequestPlanMeetingDto()
            ).data
        }
        emit(result.getOrThrow())
    }
}
