package com.capstone2.data.di

import com.capstone2.data.repository.AudioRepositoryImpl
import com.capstone2.data.repository.AuthRepositoryImpl
import com.capstone2.data.repository.SessionLocalRepositoryImpl
import com.capstone2.data.repository.SessionRemoteRepositoryImpl
import com.capstone2.data.repository.TokenRepositoryImpl
import com.capstone2.domain.repository.AudioRepository
import com.capstone2.domain.repository.AuthRepository
import com.capstone2.domain.repository.SessionLocalRepository
import com.capstone2.domain.repository.SessionRemoteRepository
import com.capstone2.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindsTokenRepository(impl: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindsAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindsSessionLocalRepository(impl: SessionLocalRepositoryImpl): SessionLocalRepository

    @Binds
    @Singleton
    abstract fun bindsSessionRemoteRepository(impl: SessionRemoteRepositoryImpl): SessionRemoteRepository

    @Binds
    @Singleton
    abstract fun bindsAudioRepository(impl: AudioRepositoryImpl): AudioRepository
}