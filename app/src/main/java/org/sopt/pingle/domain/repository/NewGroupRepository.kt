package org.sopt.pingle.domain.repository

import org.sopt.pingle.domain.model.NewGroupCheckNameEntity
import org.sopt.pingle.domain.model.NewGroupKeywordsEntity

interface NewGroupRepository {
    suspend fun getNewGroupCheckName(teamName: String): Result<NewGroupCheckNameEntity>
    suspend fun getNewGroupKeywords(): Result<List<NewGroupKeywordsEntity>>
}
