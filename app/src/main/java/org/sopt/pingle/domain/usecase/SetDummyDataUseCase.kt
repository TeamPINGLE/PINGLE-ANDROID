package org.sopt.pingle.domain.usecase

import org.sopt.pingle.domain.repository.DummyRepository

class SetDummyDataUseCase(
    private val dummyRepository: DummyRepository
) {
    operator fun invoke(dummy: Int) = dummyRepository.setDummyData(dummy = dummy)
}
