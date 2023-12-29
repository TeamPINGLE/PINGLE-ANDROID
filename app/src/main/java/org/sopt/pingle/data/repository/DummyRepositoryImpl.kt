package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.local.DummyLocalDataSource
import org.sopt.pingle.data.datasource.remote.DummyRemoteDataSource
import org.sopt.pingle.domain.model.UserEntity
import org.sopt.pingle.domain.repository.DummyRepository

class DummyRepositoryImpl @Inject constructor(
    private val dummyLocalDataSource: DummyLocalDataSource,
    private val dummyRemoteDataSource: DummyRemoteDataSource
) : DummyRepository {
    override fun setDummyData(dummy: Int) {
        dummyLocalDataSource.setDummyData(dummy = dummy)
    }

    override suspend fun getDummyUserList(page: Int): Flow<List<UserEntity>> = flow {
        val result = runCatching {
            dummyRemoteDataSource.getDummyUserList(page = page).toUserEntityList()
        }
        emit(result.getOrThrow())
    }
}
