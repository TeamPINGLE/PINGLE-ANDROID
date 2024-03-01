package org.sopt.pingle.data.datasource.local

interface PingleLocalDataSource {
    var isLogin: Boolean
    var userName: String
    var accessToken: String
    var refreshToken: String
    var groupId: Int
    var groupName: String
    var meetingCount: String
    var participantCount: String
    fun clear(): Unit
}
