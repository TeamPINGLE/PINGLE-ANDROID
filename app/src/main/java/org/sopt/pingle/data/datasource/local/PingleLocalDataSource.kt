package org.sopt.pingle.data.datasource.local

interface PingleLocalDataSource {
    var isLogin: Boolean
    var userName: String
    var accessToken: String
    var refreshToken: String
    fun clear(): Unit
}
