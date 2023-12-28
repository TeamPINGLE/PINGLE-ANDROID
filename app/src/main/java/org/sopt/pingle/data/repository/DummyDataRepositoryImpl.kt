package org.sopt.pingle.data.repository

import org.sopt.pingle.data.datasource.local.DummyLocalDataSource
import org.sopt.pingle.domain.repository.DummyDataRepository
import javax.inject.Inject

class DummyDataRepositoryImpl @Inject constructor(
    private val dummyLocalDataSource: DummyLocalDataSource
) : DummyDataRepository {
    override fun setDummyData(dummy: Int) {
        dummyLocalDataSource.setDummyData(dummy = dummy)
    }
}
