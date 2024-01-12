package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.JoinGroupRemoteDataSource
import org.sopt.pingle.data.mapper.toRequestJoinGroupCode
import org.sopt.pingle.domain.model.GroupEntity
import org.sopt.pingle.domain.model.JoinGroupCodeEntity
import org.sopt.pingle.domain.model.JoinGroupInfoEntity
import org.sopt.pingle.domain.model.JoinGroupSearchEntity
import org.sopt.pingle.domain.repository.JoinGroupRepository

class JoinGroupRepositoryImpl @Inject constructor(
    private val joinGroupRemoteDataSource: JoinGroupRemoteDataSource
) : JoinGroupRepository {
    override fun getJoinGroupSearch(teamName: String): Flow<List<JoinGroupSearchEntity>> = flow {
        val result = runCatching {
            joinGroupRemoteDataSource.getJoinGroupSearch(teamName = teamName).data.map { joinGroupSearch ->
                joinGroupSearch.toJoinGroupSearchEntity()
            }
        }
        emit(result.getOrThrow())
    }

    override fun getJoinGroupInfo(teamId: Int): Flow<JoinGroupInfoEntity> = flow {
        val result = runCatching {
            joinGroupRemoteDataSource.getJoinGroupInfo(teamId = teamId).data.toJoinGroupCodeEntity()
        }
        emit(result.getOrThrow())
    }

    override fun postJoinGroupCode(
        teamId: Int,
        joinGroupCodeEntity: JoinGroupCodeEntity
    ): Flow<GroupEntity> = flow {
        val result = runCatching {
            joinGroupRemoteDataSource.postJoinGroupCode(
                teamId = teamId,
                requestJoinGroupCodeDto = joinGroupCodeEntity.toRequestJoinGroupCode()
            ).data.toResponseJoinGroupCode()
        }
        emit(result.getOrThrow())
    }
}
