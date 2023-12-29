package org.sopt.pingle.data.repository

import javax.inject.Inject
import org.sopt.pingle.data.datasource.local.DummyLocalDataSource
import org.sopt.pingle.domain.repository.DummyDataRepository

class DummyDataRepositoryImpl @Inject constructor(
    private val dummyLocalDataSource: DummyLocalDataSource
) : DummyDataRepository {
    override fun setDummyData(dummy: Int) {
        dummyLocalDataSource.setDummyData(dummy = dummy)
    }
}
