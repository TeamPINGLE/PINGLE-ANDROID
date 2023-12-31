package org.sopt.pingle.domain.repository

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.UserEntity

interface DummyRepository {
    fun setDummyData(dummy: Int)
    suspend fun getDummyUserList(page: Int): Flow<List<UserEntity>>
}
