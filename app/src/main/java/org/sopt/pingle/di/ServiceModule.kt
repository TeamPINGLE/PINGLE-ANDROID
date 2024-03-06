package org.sopt.pingle.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.pingle.data.service.AuthService
import org.sopt.pingle.data.service.JoinGroupService
import org.sopt.pingle.data.service.MainListService
import org.sopt.pingle.data.service.MapService
import org.sopt.pingle.data.service.ParticipantService
import org.sopt.pingle.data.service.PingleService
import org.sopt.pingle.data.service.PlanService
import org.sopt.pingle.data.service.RankingService
import org.sopt.pingle.di.qualifier.Pingle
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesAuthService(@Pingle retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    @Provides
    @Singleton
    fun providesJoinGroupService(@Pingle retrofit: Retrofit): JoinGroupService =
        retrofit.create(JoinGroupService::class.java)

    @Provides
    @Singleton
    fun providesMainListService(@Pingle retrofit: Retrofit): MainListService =
        retrofit.create(MainListService::class.java)

    @Provides
    @Singleton
    fun providesMapService(@Pingle retrofit: Retrofit): MapService =
        retrofit.create(MapService::class.java)

    @Provides
    @Singleton
    fun providesParticipantService(@Pingle retrofit: Retrofit): ParticipantService =
        retrofit.create(ParticipantService::class.java)

    @Provides
    @Singleton
    fun providesPingleService(@Pingle retrofit: Retrofit): PingleService =
        retrofit.create(PingleService::class.java)

    @Provides
    @Singleton
    fun providesPlanService(@Pingle retrofit: Retrofit): PlanService =
        retrofit.create(PlanService::class.java)

    @Provides
    @Singleton
    fun providesRankingService(@Pingle retrofit: Retrofit): RankingService =
        retrofit.create(RankingService::class.java)
}
