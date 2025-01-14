package org.sopt.pingle.data.datasource.local

import android.content.SharedPreferences

interface PingleLocalDataSource {
    val sharedPreference: SharedPreferences
    var isLogin: Boolean
    var userName: String
    var accessToken: String
    var refreshToken: String
    var groupId: Int
    var groupName: String
    fun clear(): Unit
}
