package com.capstone2.data.di

import com.capstone2.data.datasource.local.TokenLocalDataSource
import com.capstone2.data.datasource.remote.AuthRemoteDataSource
import com.ilgusu.data.datasource.local.TokenLocalDataSourceImpl
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
    abstract fun bindAuthRemoteDataSource(
        authRemoteDataSource: AuthRemoteDataSource
    ): AuthRemoteDataSource
}