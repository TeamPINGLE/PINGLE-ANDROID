package org.sopt.pingle.domain.usecase

import org.sopt.pingle.domain.repository.DummyDataRepository

class SetDummyDataUseCase(
    private val dummyDataRepository: DummyDataRepository
) {
    operator fun invoke(dummy: Int) = dummyDataRepository.setDummyData(dummy = dummy)
}
