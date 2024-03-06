package org.sopt.pingle.data.repository

import javax.inject.Inject
import org.sopt.pingle.data.datasource.remote.MyGroupListRemoteDataSource
import org.sopt.pingle.domain.model.MyGroupEntity
import org.sopt.pingle.domain.repository.MyGroupListRepository

class MyGroupListRepositoryImpl @Inject constructor(
    private val myGroupListRemoteDataSource: MyGroupListRemoteDataSource
) : MyGroupListRepository {
    override suspend fun getMyGroupList(): Result<List<MyGroupEntity>> = runCatching {
        myGroupListRemoteDataSource.getMyGroupList().data.map { myGroup -> myGroup.toResponseMyGroupEntity() }
    }
}
