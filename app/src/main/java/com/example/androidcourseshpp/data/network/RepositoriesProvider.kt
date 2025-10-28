package com.example.androidcourseshpp.data.network

import com.example.androidcourseshpp.data.network.repository.auth.AuthRepository
import com.example.androidcourseshpp.data.network.repository.user.UserRepository

interface RepositoriesProvider {

    fun getAuthRepository(): AuthRepository
    fun getUserRepository(): UserRepository
}