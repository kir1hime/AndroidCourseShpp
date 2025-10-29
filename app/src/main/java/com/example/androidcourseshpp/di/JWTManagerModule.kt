package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.data.network.jwt.JWTManager
import com.example.androidcourseshpp.data.network.jwt.JWTManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface JWTManagerModule {

    @Binds
    fun provideJWTManager(jwtManagerImpl: JWTManagerImpl): JWTManager
}