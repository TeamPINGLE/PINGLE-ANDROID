package org.sopt.pingle.di

import com.kakao.sdk.user.UserApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object KakaoModule {
    @Provides
    fun provideUserApiClient(): UserApiClient = UserApiClient.instance
}