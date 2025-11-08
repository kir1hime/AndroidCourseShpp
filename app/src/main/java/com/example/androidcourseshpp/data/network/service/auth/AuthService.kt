package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.service.auth.entity.SignUpData
import com.example.androidcourseshpp.data.network.service.auth.entity.AuthResponseEntity
import com.example.androidcourseshpp.data.network.service.auth.entity.SignInData

interface AuthService {

    suspend fun signUp(data: SignUpData): AuthResponseEntity

    suspend fun singIn(data: SignInData): AuthResponseEntity
}