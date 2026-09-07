package com.example.androidcourseshpp.data.source.network.service.auth

import com.example.androidcourseshpp.data.source.network.api.auth.AuthAPI
import com.example.androidcourseshpp.data.source.network.mapper.toSignInRequestDTO
import com.example.androidcourseshpp.data.source.network.mapper.toSignInResponseModel
import com.example.androidcourseshpp.data.source.network.mapper.toSignUpResponseModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignInRequestModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpRequestModel
import com.example.androidcourseshpp.data.source.network.service.BaseRetrofitService
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthServiceImpl @Inject constructor(
    private val authApi: AuthAPI
) : BaseRetrofitService(), AuthService {


    override suspend fun signUp(signUpRequestModel: SignUpRequestModel) =
        processRetrofitExceptions {
            with(signUpRequestModel) {
                authApi.signUp(
                    email = email.toRequestBody(),
                    password = password.toRequestBody(),
                    name = userName.toRequestBody(),
                    phone = mobilePhone.toRequestBody(),
                    image = image
                ).toSignUpResponseModel()
            }
        }

    override suspend fun singIn(signInRequestModel: SignInRequestModel) =
        processRetrofitExceptions {
            authApi.singIn(signInRequestDTO = signInRequestModel.toSignInRequestDTO())
                .toSignInResponseModel()
        }
}