package com.example.androidcourseshpp.data.network.repository.auth

import com.example.androidcourseshpp.data.network.repository.auth.entity.SignUpData
import com.example.androidcourseshpp.data.network.repository.auth.entity.SignUpResponseEntity

interface AuthRepository {

    suspend fun signUp(data: SignUpData): SignUpResponseEntity
}