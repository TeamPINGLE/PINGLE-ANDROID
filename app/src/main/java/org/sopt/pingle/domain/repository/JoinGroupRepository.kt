package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.JoinGroupInfoEntity
import org.sopt.pingle.domain.model.JoinGroupSearchEntity
import org.sopt.pingle.domain.model.RequestJoinGroupCodeEntity
import org.sopt.pingle.domain.model.ResponseJoinGroupCodeEntity

interface JoinGroupRepository {
    fun getJoinGroupSearch(teamName: String): Flow<List<JoinGroupSearchEntity>>
    fun getJoinGroupInfo(teamId: Int): Flow<JoinGroupInfoEntity>
    fun postJoinGroupCode(
        teamId: Int,
        requestJoinGroupCode: RequestJoinGroupCodeEntity
    ): Flow<ResponseJoinGroupCodeEntity>
}
