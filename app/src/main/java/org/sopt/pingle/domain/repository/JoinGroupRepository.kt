package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.GroupEntity
import org.sopt.pingle.domain.model.JoinGroupCodeEntity
import org.sopt.pingle.domain.model.JoinGroupInfoEntity
import org.sopt.pingle.domain.model.JoinGroupSearchEntity

interface JoinGroupRepository {
    suspend fun getJoinGroupSearch(teamName: String): Flow<List<JoinGroupSearchEntity>>
    suspend fun getJoinGroupInfo(teamId: Int): Flow<JoinGroupInfoEntity>
    suspend fun postJoinGroupCode(
        teamId: Int,
        joinGroupCodeEntity: JoinGroupCodeEntity
    ): Flow<GroupEntity>
}
