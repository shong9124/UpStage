package com.capstone2.data.di

import com.capstone2.data.datasource.local.SessionLocalDataSource
import com.capstone2.data.datasource.local.SessionLocalDataSourceImpl
import com.capstone2.data.datasource.local.TokenLocalDataSource
import com.capstone2.data.datasource.remote.AuthRemoteDataSource
import com.capstone2.data.datasource.remote.AuthRemoteDataSourceImpl
import com.capstone2.data.datasource.local.TokenLocalDataSourceImpl
import com.capstone2.data.datasource.remote.AudioRemoteDataSource
import com.capstone2.data.datasource.remote.AudioRemoteDataSourceImpl
import com.capstone2.data.datasource.remote.SessionRemoteDataSource
import com.capstone2.data.datasource.remote.SessionRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindTokenLocalDataSource(
        tokenLocalDataSourceImpl: TokenLocalDataSourceImpl
    ): TokenLocalDataSource

    @Binds
    @Singleton
    abstract fun bindSessionLocalDataSource(
        sessionLocalDataSourceImpl: SessionLocalDataSourceImpl
    ): SessionLocalDataSource

    @Binds
    @Singleton
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSourceImpl: AuthRemoteDataSourceImpl
    ): AuthRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSessionRemoteDataSource(
        sessionRemoteDataSourceImpl: SessionRemoteDataSourceImpl
    ): SessionRemoteDataSource

    @Binds
    @Singleton
    abstract fun bindAudioRemoteDataSource(
        audioRemoteDataSource: AudioRemoteDataSourceImpl
    ): AudioRemoteDataSource
}