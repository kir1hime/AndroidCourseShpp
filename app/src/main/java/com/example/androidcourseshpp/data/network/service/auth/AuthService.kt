package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.service.auth.entity.SignUpData
import com.example.androidcourseshpp.data.network.service.auth.entity.SignUpResponseEntity

interface AuthService {

    suspend fun signUp(data: SignUpData): SignUpResponseEntity
}