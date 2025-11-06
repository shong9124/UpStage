package com.capstone2.data.di

import com.capstone2.data.BuildConfig
import com.capstone2.data.util.PrettyJsonLogger
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ClientModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor(
        PrettyJsonLogger()
    ).apply {
        if(BuildConfig.DEBUG) setLevel(HttpLoggingInterceptor.Level.BODY)
        else setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS) // 연결 타임아웃
            .readTimeout(30, TimeUnit.SECONDS)    // 응답 대기 타임아웃
            .writeTimeout(30, TimeUnit.SECONDS)   // 요청 쓰기 타임아웃
            .build()
    }

    @Singleton
    @Provides
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .build()
    }
}