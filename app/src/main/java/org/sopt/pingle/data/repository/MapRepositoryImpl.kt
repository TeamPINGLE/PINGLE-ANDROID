package org.sopt.pingle.data.repository

import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.sopt.pingle.data.datasource.remote.MapRemoteDataSource
import org.sopt.pingle.domain.model.PinEntity
import org.sopt.pingle.domain.model.PingleEntity
import org.sopt.pingle.domain.repository.MapRepository

class MapRepositoryImpl @Inject constructor(
    private val mapDataSource: MapRemoteDataSource
) : MapRepository {
    override suspend fun getPinListWithoutFiltering(
        teamId: Long,
        category: String?
    ): Flow<List<PinEntity>> = flow {
        val result = runCatching {
            mapDataSource.getPinListWithoutFiltering(
                teamId = teamId,
                category = category
            ).data.map { pin ->
                pin.toPinEntity()
            }
        }
        emit(result.getOrThrow())
    }

    override suspend fun getPingleList(teamId: Long, pinId: Long): Flow<List<PingleEntity>> = flow {
        val result = runCatching {
            mapDataSource.getPingleList(
                teamId = teamId,
                pinId = pinId
            ).data.map { pingle ->
                pingle.toPingleEntity()
            }
        }
        emit(result.getOrThrow())
    }
}
