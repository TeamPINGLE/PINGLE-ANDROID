package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.MapRemoteDataSource
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.repository.MapRepository

class MapRepositoryImpl @Inject constructor(
    private val mapRemoteDataSource: MapRemoteDataSource
) : MapRepository {
    override suspend fun getPinListWithoutFiltering(
        teamId: Long,
        category: String?,
        searchWord: String?
    ): Flow<List<PinEntity>> = flow {
        val result = runCatching {
            mapRemoteDataSource.getPinListWithoutFiltering(
                teamId = teamId,
                category = category,
                searchWord = searchWord
            ).data.map { pin ->
                pin.toPinEntity()
            }
        }
        emit(result.getOrThrow())
    }

    override suspend fun getPingleList(teamId: Long, pinId: Long, category: String?): Flow<List<PingleEntity>> = flow {
        val result = runCatching {
            mapRemoteDataSource.getPingleList(
                teamId = teamId,
                pinId = pinId,
                category = category
            ).data.map { pingle ->
                pingle.toPingleEntity()
            }
        }
        emit(result.getOrThrow())
    }
}
