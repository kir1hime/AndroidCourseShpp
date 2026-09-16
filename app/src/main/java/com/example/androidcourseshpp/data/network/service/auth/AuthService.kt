package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.model.auth.SignInRequestModel
import com.example.androidcourseshpp.data.network.model.auth.SignInResponseModel
import com.example.androidcourseshpp.data.network.model.auth.SignUpRequestModel
import com.example.androidcourseshpp.data.network.model.auth.SignUpResponseModel
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface AuthService {

    suspend fun signUp(signUpRequestModel: SignUpRequestModel): Result<SignUpResponseModel, DataError.NetworkError>

    suspend fun singIn(signInRequestModel: SignInRequestModel): Result<SignInResponseModel, DataError.NetworkError>
}