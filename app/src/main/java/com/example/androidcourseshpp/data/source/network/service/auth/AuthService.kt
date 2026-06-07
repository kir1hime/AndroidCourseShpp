package com.example.androidcourseshpp.data.source.network.service.auth

import com.example.androidcourseshpp.data.source.network.model.auth.SignInRequestModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignInResponseModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpRequestModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpResponseModel

interface AuthService {

    suspend fun signUp(data: SignUpRequestModel): SignUpResponseModel

    suspend fun singIn(data: SignInRequestModel): SignInResponseModel
}