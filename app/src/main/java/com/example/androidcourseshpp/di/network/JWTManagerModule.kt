package com.example.androidcourseshpp.di.network

import com.example.androidcourseshpp.data.source.network.jwt.JWTManager
import com.example.androidcourseshpp.data.source.network.jwt.JWTManagerImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface JWTManagerModule {

    @Binds
    @Singleton
    fun bindJWTManager(jwtManagerImpl: JWTManagerImpl): JWTManager
}