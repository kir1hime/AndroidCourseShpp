package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.api.auth.AuthAPI
import com.example.androidcourseshpp.data.network.mapper.toSignInRequestDTO
import com.example.androidcourseshpp.data.network.mapper.toSignInResponseModel
import com.example.androidcourseshpp.data.network.mapper.toSignUpResponseModel
import com.example.androidcourseshpp.data.network.model.auth.SignInRequestModel
import com.example.androidcourseshpp.data.network.model.auth.SignUpRequestModel
import com.example.androidcourseshpp.data.network.utils.safeApiCall
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthServiceImpl @Inject constructor(
    private val authApi: AuthAPI
) : AuthService {

    override suspend fun signUp(signUpRequestModel: SignUpRequestModel) = safeApiCall {
        with(signUpRequestModel) {
            val response = authApi.signUp(
                email = email.toRequestBody(),
                password = password.toRequestBody(),
                name = userName.toRequestBody(),
                phone = mobilePhone.toRequestBody(),
                image = image
            )

            response.toSignUpResponseModel()
        }
    }

    override suspend fun singIn(signInRequestModel: SignInRequestModel) = safeApiCall {
        val response = authApi.singIn(signInRequestModel.toSignInRequestDTO())
        response.toSignInResponseModel()
    }
}