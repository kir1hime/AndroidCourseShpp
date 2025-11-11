package com.example.androidcourseshpp.data.network

import com.example.androidcourseshpp.data.network.service.auth.AuthService
import com.example.androidcourseshpp.data.network.service.refresh.RefreshTokenService
import com.example.androidcourseshpp.data.network.service.user.UserService

interface ServicesProvider {

    fun getAuthService(): AuthService
    fun getUserService(): UserService

    fun getRefreshTokenService(): RefreshTokenService
}