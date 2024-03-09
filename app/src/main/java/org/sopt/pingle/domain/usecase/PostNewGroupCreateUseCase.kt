package org.sopt.pingle.domain.usecase

import org.sopt.pingle.data.model.remote.request.RequestNewGroupCreateDto
import org.sopt.pingle.domain.model.NewGroupCreateEntity
import org.sopt.pingle.domain.repository.NewGroupRepository

class PostNewGroupCreateUseCase(
    private val newGroupRepository: NewGroupRepository
) {
    suspend operator fun invoke(requestNewGroupCreateDto: RequestNewGroupCreateDto): Result<NewGroupCreateEntity> =
        newGroupRepository.postNewGroupCreate(requestNewGroupCreateDto = requestNewGroupCreateDto)
}
