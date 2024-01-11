package org.sopt.pingle.data.datasource.local

interface PingleDataSource {
    var isLogin: Boolean
    var userName: String
    var accessToken: String
    var refreshToken: String
}
