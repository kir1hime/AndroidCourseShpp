package com.example.androidcourseshpp.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DataProviderPref

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class JWTManagerPref

@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class MainRetrofit

@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class ClearRetrofit

@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class MainOkHttpClient

@Qualifier
@Retention (AnnotationRetention.BINARY)
annotation class ClearOkHttpClient