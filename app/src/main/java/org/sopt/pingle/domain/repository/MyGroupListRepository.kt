package org.sopt.pingle.domain.repository

import org.sopt.pingle.domain.model.MyGroupEntity

interface MyGroupListRepository {
    suspend fun getMyGroupList(): Result<List<MyGroupEntity>>
}
