package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.GroupEntity
import org.sopt.pingle.domain.model.JoinGroupInfoEntity
import org.sopt.pingle.domain.model.JoinGroupSearchEntity
import org.sopt.pingle.domain.model.JoinGroupCodeEntity

interface JoinGroupRepository {
    fun getJoinGroupSearch(teamName: String): Flow<List<JoinGroupSearchEntity>>
    fun getJoinGroupInfo(teamId: Int): Flow<JoinGroupInfoEntity>
    fun postJoinGroupCode(
        teamId: Int,
        joinGroupCodeEntity: JoinGroupCodeEntity
    ): Flow<GroupEntity>
}
