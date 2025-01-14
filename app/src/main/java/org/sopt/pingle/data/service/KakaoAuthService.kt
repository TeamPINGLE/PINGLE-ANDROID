package org.sopt.pingle.data.service

import android.content.ContentValues.TAG
import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject
import timber.log.Timber

class KakaoAuthService @Inject constructor(
    @ActivityContext private val context: Context,
    private val client: UserApiClient
) {
    private val isKakaoTalkLoginAvailable: Boolean
        get() = client.isKakaoTalkLoginAvailable(context)

    fun loginKakao(
        loginListener: ((String) -> Unit),
        accountListener: ((String) -> Unit)
    ) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                loginError(error)
            } else if (token != null) loginSuccess(token, loginListener, accountListener)
        }

        if (isKakaoTalkLoginAvailable) {
            client.loginWithKakaoTalk(context, callback = callback)
        } else {
            client.loginWithKakaoAccount(context, callback = callback)
        }
    }

    private fun loginError(throwable: Throwable) {
        val kakaoType = if (isKakaoTalkLoginAvailable) KAKAO_TALK else KAKAO_ACCOUNT
        Timber.d("{$kakaoType}으로 로그인 실패 ${throwable.message}")
    }

    private fun loginSuccess(
        oAuthToken: OAuthToken,
        loginListener: ((String) -> Unit),
        accountListener: ((String) -> Unit)
    ) {
        Timber.tag(KAKAO_ACCESS_TOKEN).d(oAuthToken.accessToken)
        client.me { user, error ->
            loginListener(oAuthToken.accessToken)
            if (error != null) {
                Timber.e("사용자 정보 요청 실패 $error")
            } else if (user != null) {
                accountListener(user.kakaoAccount?.profile?.nickname ?: PINGU)
            }
        }
    }

    fun logoutKakao(logoutListener: () -> Unit) {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Timber.tag(TAG).e(error.message, "카카오 로그아웃 실패. SDK에서 토큰 삭제됨")
            } else {
                logoutListener()
                Timber.tag(TAG).i("카카오 로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    fun withdrawKakao() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.tag(TAG).e(error, "카카오 회원 탈퇴 실패 : ${error.message}")
            } else {
                Timber.tag(TAG).i("카카오 회원 탈퇴 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }

    companion object {
        const val KAKAO_TALK = "카카오톡"
        const val KAKAO_ACCOUNT = "카카오계정"
        const val PINGU = "핑구"
        const val KAKAO_ACCESS_TOKEN = "카카오 엑세스 토큰"
    }
}
