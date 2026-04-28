package com.example.androidcourseshpp.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.androidcourseshpp.data.dataProvider.USER_INFO_STORE
import com.example.androidcourseshpp.data.network.jwt.JWT_STORAGE
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Provides
    @Singleton
    @DataProviderPref
    fun provideDataProviderSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(USER_INFO_STORE, MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @JWTManagerPref
    fun provideJWTManagerSharePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(JWT_STORAGE, MODE_PRIVATE)
    }
}

