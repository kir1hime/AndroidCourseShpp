package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.data.dataProvider.UserDataProvider
import com.example.androidcourseshpp.data.dataProvider.UserDataProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
interface UserDataProviderModule {

    @Singleton
    @Binds
    fun provideDataProvider(dataProviderImpl: UserDataProviderImpl): UserDataProvider
}