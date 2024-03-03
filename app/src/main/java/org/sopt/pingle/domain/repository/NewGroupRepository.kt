package org.sopt.pingle.domain.repository

import org.sopt.pingle.data.model.remote.request.RequestNewGroupCreateDto
import org.sopt.pingle.domain.model.NewGroupCheckNameEntity
import org.sopt.pingle.domain.model.NewGroupCreateEntity
import org.sopt.pingle.domain.model.NewGroupKeywordEntity

interface NewGroupRepository {
    suspend fun getNewGroupCheckName(teamName: String): Result<NewGroupCheckNameEntity>
    suspend fun getNewGroupKeywords(): Result<List<NewGroupKeywordEntity>>
    suspend fun postNewGroupCreate(requestNewGroupCreateDto: RequestNewGroupCreateDto): Result<NewGroupCreateEntity>
}
