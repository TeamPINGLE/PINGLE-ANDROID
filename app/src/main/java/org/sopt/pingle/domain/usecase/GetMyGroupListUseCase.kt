package org.sopt.pingle.domain.usecase

import org.sopt.pingle.domain.model.MyGroupEntity
import org.sopt.pingle.domain.repository.MyGroupListRepository

class GetMyGroupListUseCase(
    private val myGroupListRepository: MyGroupListRepository
) {
    suspend operator fun invoke(): Result<List<MyGroupEntity>> =
        myGroupListRepository.getMyGroupList()
}
