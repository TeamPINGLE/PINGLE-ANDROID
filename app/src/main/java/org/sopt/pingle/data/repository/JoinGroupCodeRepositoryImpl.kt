package org.sopt.pingle.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.JoinGroupCodeRemoteDataSource
import org.sopt.pingle.domain.model.JoinGroupInfoEntity
import org.sopt.pingle.domain.repository.JoinGroupCodeRepository
import javax.inject.Inject

class JoinGroupCodeRepositoryImpl @Inject constructor(
    private val joinGroupCodeRemoteDataSource: JoinGroupCodeRemoteDataSource
) : JoinGroupCodeRepository {
    override fun getJoinGroupInfo(teamId: Int): Flow<JoinGroupInfoEntity> = flow {
        val result = runCatching {
            joinGroupCodeRemoteDataSource.getJoinGroupCodeInfo(teamId = teamId).data.toJoinGroupCodeEntity()
        }
        emit(result.getOrThrow())
    }
}