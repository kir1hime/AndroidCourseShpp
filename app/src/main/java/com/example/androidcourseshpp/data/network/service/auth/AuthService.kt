package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.entity.signin.SignInData
import com.example.androidcourseshpp.data.network.entity.signin.SignInResponseEntity
import com.example.androidcourseshpp.data.network.entity.signup.SignUpData
import com.example.androidcourseshpp.data.network.entity.signup.SignUpResponseEntity

interface AuthService {

    suspend fun signUp(data: SignUpData): SignUpResponseEntity

    suspend fun singIn(data: SignInData): SignInResponseEntity
}