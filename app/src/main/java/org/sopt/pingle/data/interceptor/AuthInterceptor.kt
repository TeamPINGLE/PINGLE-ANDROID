package org.sopt.pingle.data.interceptor

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import org.sopt.pingle.data.datasource.local.PingleDataSource

class AuthInterceptor @Inject constructor(
    private val localStorage: PingleDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest =
            originalRequest.newBuilder().addHeader(AUTHORIZATION, localStorage.accessToken).build()
        val response = chain.proceed(authRequest)

        when (response.code) {
            CODE_TOKEN_EXPIRE -> {
                // TODO 토큰 재발급 api 연동
            }
        }
        return response
    }

    companion object {
        const val CODE_TOKEN_EXPIRE = 401
        const val AUTHORIZATION = "Authorization"
    }
}
