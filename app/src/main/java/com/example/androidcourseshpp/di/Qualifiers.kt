package com.example.androidcourseshpp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataProviderPref

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JWTManagerPref

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenRefreshRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class TokenRefreshOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainOkHttpClient


