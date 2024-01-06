package org.sopt.pingle.presentation.ui.auth

import android.os.Bundle
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.AndroidEntryPoint
import org.sopt.pingle.R
import org.sopt.pingle.databinding.ActivityAuthBinding
import org.sopt.pingle.util.base.BindingActivity
import timber.log.Timber

@AndroidEntryPoint
class AuthActivity : BindingActivity<ActivityAuthBinding>(R.layout.activity_auth) {
    // TODO 서버통신할 때 주석 삭제하겠습니다.!
    // 카카오계정으로 로그인 공통 callback 구성
    // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
    val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
        if (error != null) {
            Timber.e(error, "카카오계정으로 로그인 실패")
        } else if (token != null) {
            Timber.i("카카오계정으로 로그인 성공 " + token.accessToken)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        btnClickListener()
    }

    private fun btnClickListener() {
        binding.btnAuthKakao.setOnClickListener {
            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
                loginWithKakaoTalk()
            } else {
                loginWithKakaoAccount()
            }
        }
    }

    // 카카오톡 앱 로그인
    private fun loginWithKakaoTalk() {
        UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
            if (error != null) {
                Timber.e(error, "카카오톡으로 로그인 실패")

                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                    return@loginWithKakaoTalk
                }
                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                loginWithKakaoAccount()
            } else if (token != null) {
                getUserInfo()
                Timber.i("카카오톡으로 로그인 성공 " + token.accessToken)
            }
        }
    }

    // 카카오 계정 웹 로그인
    private fun loginWithKakaoAccount() {
        UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
    }

    // 사용자 정보 수집 (닉네임)
    private fun getUserInfo() {
        UserApiClient.instance.me { user, _ ->
            if (user != null) {
                var userKakaoNickname = user.kakaoAccount?.profile?.nickname
            } else {
                Timber.d("카카오 유저 정보 받기 실패")
            }
        }
    }

    // 카카오 계정 로그아웃
    private fun logoutKakao() {
        UserApiClient.instance.logout { error ->
            if (error != null) {
                Timber.e(error, "로그아웃 실패. SDK에서 토큰 삭제됨")
            } else {
                Timber.i("로그아웃 성공. SDK에서 토큰 삭제됨")
            }
        }
    }

    // 카카오 계정 삭제
    private fun deleteKakao() {
        UserApiClient.instance.unlink { error ->
            if (error != null) {
                Timber.e(error, "연결 끊기 실패")
            } else {
                Timber.i("연결 끊기 성공. SDK에서 토큰 삭제 됨")
            }
        }
    }
}
