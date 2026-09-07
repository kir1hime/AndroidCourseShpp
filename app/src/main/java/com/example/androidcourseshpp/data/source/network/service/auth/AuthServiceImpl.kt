package com.example.androidcourseshpp.data.source.network.service.auth

import com.example.androidcourseshpp.data.source.network.api.auth.AuthAPI
import com.example.androidcourseshpp.data.source.network.dto.auth.SignInRequestDTO
import com.example.androidcourseshpp.data.source.network.model.auth.SignInRequestModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpRequestModel
import com.example.androidcourseshpp.data.source.network.utils.safeApiCall
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class AuthServiceImpl @Inject constructor(
    private val authApi: AuthAPI
) : AuthService {


    override suspend fun signUp(data: SignUpRequestModel) =
        safeApiCall {
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

    override suspend fun singIn(data: SignInRequestModel) =
        safeApiCall {
            val signInRequestDTO = SignInRequestDTO(
                email = data.email,
                password = data.password
            )
            authApi.singIn(signInRequestDTO).data
        }

}