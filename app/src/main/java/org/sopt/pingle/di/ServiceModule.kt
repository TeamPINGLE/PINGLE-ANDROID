package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.pingle.data.service.AuthService
import org.sopt.pingle.data.service.DummyService
import org.sopt.pingle.di.qualifier.Pingle
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesDummyService(@Pingle retrofit: Retrofit): DummyService =
        retrofit.create(DummyService::class.java)

    @Provides
    @Singleton
    fun providesAuthService(@Pingle retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)
}
