package com.example.androidcourseshpp.data.source.network.service

import com.example.androidcourseshpp.data.source.network.service.ServicesProvider
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