package org.sopt.pingle.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.sopt.pingle.data.datasource.local.DummyLocalDataSource
import org.sopt.pingle.data.datasource.remote.DummyRemoteDataSource
import org.sopt.pingle.data.datasource.remote.MapRemoteDataSource
import org.sopt.pingle.data.datasource.remote.PingleRemoteDataSource
import org.sopt.pingle.data.datasourceimpl.local.DummyLocalDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.DummyRemoteDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.MapRemoteDataSourceImpl
import org.sopt.pingle.data.datasourceimpl.remote.PingleRemoteDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsDummyLocalDataSource(dummyLocalDataSourceImpl: DummyLocalDataSourceImpl): DummyLocalDataSource

    @Binds
    @Singleton
    abstract fun bindsDummyRemoteDataSource(dummyRemoteDataSourceImpl: DummyRemoteDataSourceImpl): DummyRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsMapRemoteDataSource(mapRemoteDataSourceImpl: MapRemoteDataSourceImpl): MapRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindsPingleRemoteDataSource(pingleRemoteDataSourceImpl: PingleRemoteDataSourceImpl): PingleRemoteDataSource
}
