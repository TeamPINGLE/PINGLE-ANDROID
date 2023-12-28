package org.sopt.pingle.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.DummyRemoteDataSource
import org.sopt.pingle.domain.model.UserEntity
import org.sopt.pingle.domain.repository.DummyRepository
import javax.inject.Inject

class DummyRepositoryImpl @Inject constructor(
    private val dummyRemoteDataSource: DummyRemoteDataSource
) : DummyRepository {
    override suspend fun getDummyUserList(page: Int): Flow<List<UserEntity>> = flow {
        val result = runCatching {
            dummyRemoteDataSource.getDummyUserList(page = page).toUserEntityList()
        }
        emit(result.getOrThrow())
    }
}
