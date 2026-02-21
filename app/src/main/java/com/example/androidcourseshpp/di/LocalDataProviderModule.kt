package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.data.source.local.userdata.DatabaseSyncProvider
import com.example.androidcourseshpp.data.source.local.userdata.GalleryDataProvider
import com.example.androidcourseshpp.data.source.local.userdata.UserDataProvider
import com.example.androidcourseshpp.data.source.local.userdata.LocalDataProvider
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocalDataProviderModule {

    @Binds
    @Singleton
    fun bindUserDataProvider(localDataProvider: LocalDataProvider): UserDataProvider

    @Binds
    @Singleton
    fun bindGalleryDataProvider(localDataProvider: LocalDataProvider): GalleryDataProvider

    @Binds
    @Singleton
    fun bindDatabaseSyncProvider(localDataProvider: LocalDataProvider): DatabaseSyncProvider
}