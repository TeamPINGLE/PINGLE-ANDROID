package org.sopt.pingle.domain.repository

import org.sopt.pingle.domain.model.NewGroupKeywordsEntity

interface NewGroupRepository {
    suspend fun getNewGroupKeywords(): Result<List<NewGroupKeywordsEntity>>
}
