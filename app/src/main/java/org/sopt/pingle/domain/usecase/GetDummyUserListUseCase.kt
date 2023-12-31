package org.sopt.pingle.domain.usecase

import kotlinx.coroutines.flow.Flow
import org.sopt.pingle.domain.model.UserEntity
import org.sopt.pingle.domain.repository.DummyRepository

class GetDummyUserListUseCase(
    private val dummyRepository: DummyRepository
) {
    suspend operator fun invoke(page: Int): Flow<List<UserEntity>> =
        dummyRepository.getDummyUserList(page = page)
}
