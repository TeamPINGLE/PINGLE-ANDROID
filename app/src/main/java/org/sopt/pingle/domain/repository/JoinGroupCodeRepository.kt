package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.JoinGroupInfoEntity

interface JoinGroupCodeRepository {
    fun getJoinGroupInfo(teamId: Int): Flow<JoinGroupInfoEntity>
}