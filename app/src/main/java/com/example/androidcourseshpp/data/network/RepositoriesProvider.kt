package com.example.androidcourseshpp.data.network

import com.example.androidcourseshpp.data.network.repository.auth.AuthRepository

interface RepositoriesProvider {

    fun getAuthRepository(): AuthRepository
}