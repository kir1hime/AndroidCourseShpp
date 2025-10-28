package com.example.androidcourseshpp.data.network

import com.example.androidcourseshpp.data.network.repository.auth.AuthRepositoryImpl
import com.example.androidcourseshpp.data.network.repository.user.UserRepositoryImpl

class RetrofitRepositoryProvider(private val config: RetrofitConfig) : RepositoriesProvider {

    override fun getAuthRepository() = AuthRepositoryImpl(config)
    override fun getUserRepository() = UserRepositoryImpl(config)
}