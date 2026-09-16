package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.model.auth.SignInRequestModel
import com.example.androidcourseshpp.data.network.model.auth.SignInResponseModel
import com.example.androidcourseshpp.data.network.model.auth.SignUpRequestModel
import com.example.androidcourseshpp.data.network.model.auth.SignUpResponseModel

interface AuthService {

    suspend fun signUp(data: SignUpRequestModel): SignUpResponseModel

    suspend fun singIn(data: SignInRequestModel): SignInResponseModel
}