package org.sopt.pingle.data.interceptor

import android.app.Application
import android.content.Intent
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.sopt.pingle.BuildConfig
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.data.model.remote.response.ResponseReissueDTO
import org.sopt.pingle.util.base.BaseResponse

class AuthInterceptor @Inject constructor(
    private val json: Json,
    private val localStorage: PingleLocalDataSource,
    private val context: Application
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest =
            if (localStorage.isLogin) originalRequest.newAuthBuilder() else originalRequest
        val response = chain.proceed(authRequest)

        when (response.code) {
            CODE_TOKEN_EXPIRE -> {
                response.close()
                val refreshTokenRequest = originalRequest.newBuilder().get()
                    .url("${BuildConfig.BASE_URL}/v1/auth/reissue")
                    .post("".toRequestBody(null))
                    .addHeader(AUTHORIZATION, localStorage.refreshToken)
                    .build()
                val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                if (refreshTokenResponse.isSuccessful) {
                    val responseRefresh =
                        json.decodeFromString<BaseResponse<ResponseReissueDTO>>(
                            refreshTokenResponse.body?.string()
                                ?: throw IllegalStateException("\"refreshTokenResponse is null $refreshTokenResponse\"")
                        )

                    with(localStorage) {
                        accessToken = BEARER + responseRefresh.data.accessToken
                        refreshToken = BEARER + responseRefresh.data.refreshToken
                    }

                    refreshTokenResponse.close()

                    val newRequest = originalRequest.newAuthBuilder()
                    return chain.proceed(newRequest)
                } else {
                    with(context) {
                        CoroutineScope(Dispatchers.Main).launch {
                            startActivity(
                                Intent.makeRestartActivityTask(
                                    packageManager.getLaunchIntentForPackage(packageName)?.component
                                )
                            )
                            localStorage.clear()
                        }
                    }
                }
            }
        }
        return response
    }

    private fun Request.newAuthBuilder() =
        this.newBuilder().addHeader(AUTHORIZATION, localStorage.accessToken).build()

    companion object {
        const val CODE_TOKEN_EXPIRE = 401
        const val AUTHORIZATION = "Authorization"
        const val BEARER = "Bearer "
    }
}
