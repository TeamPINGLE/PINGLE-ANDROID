package org.sopt.pingle.data.datasourceimpl.local

import android.content.SharedPreferences
import androidx.core.content.edit
import org.sopt.pingle.data.datasource.local.PingleDataSource
import javax.inject.Inject

class PingleDataSourceImpl @Inject constructor(
    private val pinlgePref: SharedPreferences,
) : PingleDataSource {
    override var userName: String
        get() = pinlgePref.getString(USER_NAME, "") ?: ""
        set(value) = pinlgePref.edit { putString(USER_NAME, value) }

    override var accessToken: String
        get() = pinlgePref.getString(ACCESS_TOKEN, "") ?: ""
        set(value) = pinlgePref.edit { putString(ACCESS_TOKEN, value) }

    override var refreshToken: String
        get() = pinlgePref.getString(REFRESH_TOKEN, "") ?: ""
        set(value) = pinlgePref.edit { putString(REFRESH_TOKEN, value) }

    companion object {
        const val USER_NAME = "userName"
        const val ACCESS_TOKEN = "accessToken"
        const val REFRESH_TOKEN = "refreshToken"
    }
}