package org.sopt.pingle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import org.sopt.pingle.data.datasource.local.PingleLocalDataSource
import org.sopt.pingle.data.datasource.remote.AuthRemoteDataSource
import org.sopt.pingle.data.datasource.remote.JoinGroupRemoteDataSource
import org.sopt.pingle.data.datasource.remote.MapRemoteDataSource
import org.sopt.pingle.data.datasource.remote.ParticipantRemoteDataSource
import org.sopt.pingle.data.datasource.remote.PingleRemoteDataSource
import org.sopt.pingle.data.datasource.remote.PlanRemoteDataSource
import org.sopt.pingle.data.datasource.remote.RankingRemoteDataSource
import org.sopt.pingle.data.datasourceimpl.local.PingleLocalDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.AuthRemoteDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.JoinGroupRemoteDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.MapRemoteDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.ParticipantRemoteDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.PingleRemoteDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.PlanRemoteDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.RankingRemoteDataSourceImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsPingleLocalDataSource(pingleLocalDataSourceImpl: PingleLocalDataSourceImpl): PingleLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsAuthRemoteDataSource(authRemoteDataSourceImpl: AuthRemoteDataSourceImpl): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsJoinGroupRemoteDataSource(joinGroupRemoteDataSourceImpl: JoinGroupRemoteDataSourceImpl): JoinGroupRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsMapRemoteDataSource(mapRemoteDataSourceImpl: MapRemoteDataSourceImpl): MapRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsParticipantRemoteDataSource(participantRemoteDataSourceImpl: ParticipantRemoteDataSourceImpl): ParticipantRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsPingleRemoteDataSource(pingleRemoteDataSourceImpl: PingleRemoteDataSourceImpl): PingleRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsPlanRemoteDataSource(planRemoteDataSourceImpl: PlanRemoteDataSourceImpl): PlanRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsRankingRemoteDataSource(rankingRemoteDataSourceImpl: RankingRemoteDataSourceImpl): RankingRemoteDataSource
}
