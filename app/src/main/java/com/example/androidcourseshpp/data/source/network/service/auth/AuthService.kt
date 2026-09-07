package com.example.androidcourseshpp.data.source.network.service.auth

import com.example.androidcourseshpp.data.source.network.model.auth.SignInRequestModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignInResponseModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpRequestModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpResponseModel
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface AuthService {

    suspend fun signUp(data: SignUpRequestModel): Result<SignUpResponseModel, DataError.Network>

    suspend fun singIn(data: SignInRequestModel): Result<SignInResponseModel, DataError.Network>
}