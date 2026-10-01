package com.example.androidcourseshpp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class UserInfoPreferences

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JWTManagerPreferences

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


