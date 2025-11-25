package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.service.auth.entity.SignUpData
import com.example.androidcourseshpp.data.network.service.auth.entity.SignUpResponseEntity
import com.example.androidcourseshpp.data.network.service.auth.entity.SignInData

interface AuthService {

    suspend fun signUp(data: SignUpData): SignUpResponseEntity

    suspend fun singIn(data: SignInData): SignUpResponseEntity
}