package com.example.androidcourseshpp.data.network

import javax.inject.Inject
import javax.inject.Singleton

const val BASE_URL = "http://178.63.9.114:7777/api/"


@Singleton
class RetrofitServiceProviderHolder @Inject constructor(
    private val servicesProvider: ServicesProvider
) {
    val serviceProvider: ServicesProvider by lazy {
        servicesProvider
    }
}