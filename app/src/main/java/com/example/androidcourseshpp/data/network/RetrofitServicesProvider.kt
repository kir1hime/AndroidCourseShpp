package com.example.androidcourseshpp.data.network

import com.example.androidcourseshpp.data.network.service.auth.AuthServiceImpl
import com.example.androidcourseshpp.data.network.service.user.UserServiceImpl
import jakarta.inject.Inject
import jakarta.inject.Singleton

class RetrofitServicesProvider @Inject constructor(private val config: RetrofitConfig) :
    ServicesProvider {

    override fun getAuthService() = AuthServiceImpl(config)
    override fun getUserService() = UserServiceImpl(config)
}