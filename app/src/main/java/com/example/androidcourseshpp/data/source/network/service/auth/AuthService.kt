package com.example.androidcourseshpp.data.source.network.service.auth

import com.example.androidcourseshpp.data.source.network.entity.auth.SignUpData
import com.example.androidcourseshpp.data.source.network.entity.auth.SignUpResponseEntity
import com.example.androidcourseshpp.data.source.network.entity.auth.SignInData
import com.example.androidcourseshpp.data.source.network.entity.auth.SignInResponseEntity

interface AuthService {

    suspend fun signUp(data: SignUpData): SignUpResponseEntity

    suspend fun singIn(data: SignInData): SignInResponseEntity
}