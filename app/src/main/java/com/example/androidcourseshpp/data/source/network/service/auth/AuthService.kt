package com.example.androidcourseshpp.data.source.network.service.auth

import com.example.androidcourseshpp.data.source.network.entity.auth.SignUpData
import com.example.androidcourseshpp.data.source.network.entity.auth.SignUpResponseModel
import com.example.androidcourseshpp.data.source.network.entity.auth.SignInData
import com.example.androidcourseshpp.data.source.network.entity.auth.SignInResponseEntity

interface AuthService {

    suspend fun signUp(data: SignUpData): SignUpResponseModel

    suspend fun singIn(data: SignInData): SignInResponseEntity
}