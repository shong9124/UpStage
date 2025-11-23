package com.capstone2.data.di

import com.capstone2.data.service.AudioService
import com.capstone2.data.service.AuthService
import com.capstone2.data.service.SessionService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

    @Provides
    fun providesAuthService(
        client: Retrofit
    ): AuthService = client.create(AuthService::class.java)

    @Provides
    fun providesSessionService(
        client: Retrofit
    ): SessionService = client.create(SessionService::class.java)

    @Provides
    fun providesAudioService(
        client: Retrofit
    ): AudioService = client.create(AudioService::class.java)
}