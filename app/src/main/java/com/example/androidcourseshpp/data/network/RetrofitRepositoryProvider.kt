package com.example.androidcourseshpp.data.network

import com.example.androidcourseshpp.data.network.repository.auth.AuthRepositoryImpl

class RetrofitRepositoryProvider(private val config: RetrofitConfig) : RepositoriesProvider {

   override fun getAuthRepository() = AuthRepositoryImpl(config)
}