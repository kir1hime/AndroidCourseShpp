package com.example.androidcourseshpp.data.network.repository.auth

import com.example.androidcourseshpp.data.network.repository.auth.entity.SignUpData

interface AuthRepository {

    suspend fun signUp(data: SignUpData)
}