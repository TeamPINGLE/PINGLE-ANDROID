package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.pingle.data.service.DummyService
import org.sopt.pingle.data.service.MapService
import org.sopt.pingle.data.service.PingleService
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
    fun providesMapService(@Pingle retrofit: Retrofit): MapService =
        retrofit.create(MapService::class.java)

    @Provides
    @Singleton
    fun providesPingleService(@Pingle retrofit: Retrofit): PingleService =
        retrofit.create(PingleService::class.java)
}
