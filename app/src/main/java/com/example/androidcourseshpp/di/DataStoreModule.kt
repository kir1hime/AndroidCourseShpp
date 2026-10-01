package com.example.androidcourseshpp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

const val USER_INFO_STORE = "USER_INFO_STORE"
const val JWT_STORE = "JWT_STORE"

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {

    private val Context.userInfoDataStore by preferencesDataStore(
        name = USER_INFO_STORE
    )

    private val Context.jwtDataStore by preferencesDataStore(
        name = JWT_STORE
    )

    @Singleton
    @Provides
    @UserInfoPreferences
    fun provideUserInfoDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.userInfoDataStore
    }

    @Singleton
    @Provides
    @JWTManagerPreferences
    fun provideJWTDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return context.jwtDataStore
    }
}