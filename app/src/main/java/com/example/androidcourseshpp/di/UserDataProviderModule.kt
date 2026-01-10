package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.local.userdata.UserDataProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
interface UserDataProviderModule {

    @Binds
    @Singleton
    fun bindDataProvider(dataProviderImpl: UserDataProviderImpl): UserDataProvider
}