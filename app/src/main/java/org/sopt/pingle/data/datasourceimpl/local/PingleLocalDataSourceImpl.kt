package org.sopt.pingle.data.datasourceimpl.local

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import org.sopt.pingle.BuildConfig
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource

class PingleLocalDataSourceImpl @Inject constructor(
    @ApplicationContext context: Context
) : PingleLocalDataSource {

    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val pref: SharedPreferences =
        if (BuildConfig.DEBUG) {
            context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
        } else {
            EncryptedSharedPreferences.create(
                context,
                FILE_NAME,
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

    override var isLogin: Boolean
        get() = pref.getBoolean(AUTO_LOGIN, false)
        set(value) = pref.edit { putBoolean(AUTO_LOGIN, value) }

    override var userName: String
        get() = pref.getString(USER_NAME, "") ?: ""
        set(value) = pref.edit { putString(USER_NAME, value) }

    override var accessToken: String
        get() = pref.getString(ACCESS_TOKEN, "") ?: ""
        set(value) = pref.edit { putString(ACCESS_TOKEN, value) }

    override var refreshToken: String
        get() = pref.getString(REFRESH_TOKEN, "") ?: ""
        set(value) = pref.edit { putString(REFRESH_TOKEN, value) }

    override var groupId: Int
        get() = pref.getInt(GROUP_ID, -1)
        set(value) = pref.edit { putInt(GROUP_ID, value) }

    override var groupName: String
        get() = pref.getString(GROUP_NAME, "") ?: ""
        set(value) = pref.edit { putString(GROUP_NAME, value) }

    override var meetingCount: String
        get() = pref.getString(MEETING_COUNT, "") ?: ""
        set(value) = pref.edit { putString(MEETING_COUNT, value) }

    override var participantCount: String
        get() = pref.getString(PARTICIPANTS_COUNT, "") ?: ""
        set(value) = pref.edit { putString(PARTICIPANTS_COUNT, value) }

    override fun clear() {
        pref.edit {
            clear()
        }
    }

    companion object {
        const val FILE_NAME = "AuthSharedPreferences"
        const val AUTO_LOGIN = "AutoLogin"
        const val USER_NAME = "UserName"
        const val ACCESS_TOKEN = "AccessToken"
        const val REFRESH_TOKEN = "RefreshToken"
        const val GROUP_ID = "GroupId"
        const val GROUP_NAME = "GroupName"
        const val MEETING_COUNT = "MeetingCount"
        const val PARTICIPANTS_COUNT = "ParticipantsCount"
        const val GROUP_KEYWORD = "GroupKeyWord"
        const val IS_OWNER = "IsOwner"
        const val GROUP_CODE = "GroupCode"
    }
}
