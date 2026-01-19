package com.example.androidcourseshpp.data.source.network.service.auth

import com.example.androidcourseshpp.data.source.network.model.auth.SignUpData
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpResponseModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignInData
import com.example.androidcourseshpp.data.source.network.model.auth.SignInResponseEntity

interface AuthService {

    suspend fun signUp(data: SignUpData): SignUpResponseModel

    suspend fun singIn(data: SignInData): SignInResponseEntity
}