package org.sopt.pingle.data.service

import org.sopt.pingle.data.model.remote.response.ResponseMyPingleDto
import org.sopt.pingle.util.base.BaseResponse
import org.sopt.pingle.util.base.NullableBaseResponse
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PingleService {
    @POST("$VERSION/$MEETINGS/{$MEETING_ID}/$JOIN")
    suspend fun postPingleJoin(
        @Path("$MEETING_ID") meetingId: Long
    ): NullableBaseResponse<Unit?>

    @DELETE("$VERSION/$MEETINGS/{$MEETING_ID}/$CANCEL")
    suspend fun deletePingleCancel(
        @Path("$MEETING_ID") meetingId: Long
    ): NullableBaseResponse<Unit?>

    @GET("$VERSION/$USERS/$ME/$MEETINGS")
    suspend fun getMyPingleList(
        @Header(X_TEAM_ID) teamId: Int,
        @Query(PARTICIPATION) participation: Boolean
    ): BaseResponse<List<ResponseMyPingleDto>>

    @DELETE("$VERSION/$MEETINGS/{$MEETING_ID}")
    suspend fun deletePingleDelete(
        @Path(MEETING_ID) meetingId: Long
    ): NullableBaseResponse<Unit?>

    companion object {
        const val VERSION = "v1"
        const val MEETINGS = "meetings"
        const val MEETING_ID = "meetingId"
        const val JOIN = "join"
        const val CANCEL = "cancel"
        const val USERS = "users"
        const val ME = "me"
        const val X_TEAM_ID = "X-Team-Id"
        const val PARTICIPATION = "participation"
    }
}
