package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.PlanRemoteDataSource
import org.sopt.pingle.data.mapper.toRequestPlanMeetingDto
import org.sopt.pingle.domain.model.PlanLocationEntity
import org.sopt.pingle.domain.model.PlanMeetingEntity
import org.sopt.pingle.domain.repository.PlanRepository

class PlanRepositoryImpl @Inject constructor(
    private val planRemoteDataSource: PlanRemoteDataSource
) : PlanRepository {
    override suspend fun getPlanLocationList(searchWord: String): Flow<List<PlanLocationEntity>> =
        flow {
            val result = runCatching {
                planRemoteDataSource.getPlanLocation(searchWord = searchWord).data.map { planLocation ->
                    planLocation.toPlanLocationEntity()
                }
            }
            emit(result.getOrThrow())
        }

    override suspend fun postPlanMeeting(
        teamId: Int,
        planMeetingEntity: PlanMeetingEntity
    ): Flow<Unit?> = flow {
        val result = runCatching {
            planRemoteDataSource.postPlanMeeting(
                teamId = teamId,
                requestPlanMeetingDto = planMeetingEntity.toRequestPlanMeetingDto()
            ).data
        }
        emit(result.getOrThrow())
    }
}
