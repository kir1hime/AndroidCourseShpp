package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.data.dataProvider.DataProvider
import com.example.androidcourseshpp.data.dataProvider.DataProviderImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module

@InstallIn(SingletonComponent::class)
interface DataProviderModule {

    @Singleton
    @Binds
    fun provideDataProvider(dataProviderImpl: DataProviderImpl): DataProvider
}