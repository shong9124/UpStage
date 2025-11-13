package com.capstone2.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    @Provides
    @Singleton
    @Named("tokenDataStore")
    fun provideTokenDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(TOKEN_PREFERENCES_NAME) }
        )

    @Provides
    @Singleton
    @Named("sessionDataStore")
    fun provideSessionDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(SESSION_PREFERENCES_NAME) }
        )

    companion object {
        private const val TOKEN_PREFERENCES_NAME = "token_preferences"
        private const val SESSION_PREFERENCES_NAME = "session_preferences"
    }
}