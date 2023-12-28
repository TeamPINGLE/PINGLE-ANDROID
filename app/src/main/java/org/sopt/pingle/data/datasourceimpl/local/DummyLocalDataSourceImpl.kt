package org.sopt.pingle.data.datasourceimpl.local

import android.content.SharedPreferences
import androidx.core.content.edit
import org.sopt.pingle.data.datasource.local.DummyLocalDataSource
import javax.inject.Inject

class DummyLocalDataSourceImpl @Inject constructor(
    private val dataStore: SharedPreferences
) : DummyLocalDataSource {
    override fun setDummyData(dummy: Int) {
        dataStore.edit { putInt(DUMMY, dummy) }
    }

    companion object {
        const val FILE_NAME = "pingle"
        const val DUMMY = "dummy"
    }
}
