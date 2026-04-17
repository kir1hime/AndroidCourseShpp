package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.api.auth.AuthAPI
import com.example.androidcourseshpp.data.network.dto.auth.SignInRequestDTO
import com.example.androidcourseshpp.data.network.entity.signin.SignInData
import com.example.androidcourseshpp.data.network.entity.signup.SignUpData
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthServiceImpl @Inject constructor(
    private val authApi: AuthAPI
) : BaseRetrofitService(), AuthService {


    override suspend fun signUp(data: SignUpData) =
        processRetrofitExceptions {
            with(data) {
                authApi.signUp(
                    email = email.toRequestBody(),
                    password = password.toRequestBody(),
                    name = userName.toRequestBody(),
                    phone = mobilePhone.toRequestBody(),
                    image = image
                ).data
            }
        }

    override suspend fun singIn(data: SignInData) =
        processRetrofitExceptions {
            val signInRequestDTO = SignInRequestDTO(
                email = data.email,
                password = data.password
            )
            authApi.singIn(signInRequestDTO).data
        }

}