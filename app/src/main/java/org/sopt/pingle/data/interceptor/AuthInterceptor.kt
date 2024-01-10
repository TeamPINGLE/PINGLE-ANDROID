package org.sopt.pingle.data.interceptor

import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.Response
import org.sopt.pingle.BuildConfig

class AuthInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val authRequest =
            originalRequest.newBuilder().addHeader(AUTHORIZATION, BuildConfig.ACCESS_TOKEN).build()
        val response = chain.proceed(authRequest)

        when (response.code) {
            401 -> {
                // TODO 토큰 재발급 api 연동
            }
        }
        return response
    }

    companion object {
        const val AUTHORIZATION = "Authorization"
    }
}
