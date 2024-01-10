package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.pingle.data.service.DummyService
import org.sopt.pingle.data.service.MapService
import org.sopt.pingle.data.service.PlanService
import org.sopt.pingle.di.qualifier.Pingle
import retrofit2.Retrofit
import retrofit2.create

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
    fun providesPlanService(@Pingle retrofit: Retrofit): PlanService =
        retrofit.create(PlanService::class.java)
}
