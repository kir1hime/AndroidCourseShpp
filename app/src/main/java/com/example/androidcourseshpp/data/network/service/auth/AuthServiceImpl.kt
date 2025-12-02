package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.RetrofitConfig
import com.example.androidcourseshpp.data.network.dto.auth.SignInRequestDTO
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService
import com.example.androidcourseshpp.data.network.service.auth.entity.SignInData
import com.example.androidcourseshpp.data.network.service.auth.entity.SignUpData
import com.example.androidcourseshpp.data.network.webapi.auth.AuthAPI
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


class AuthServiceImpl(
    config: RetrofitConfig
) : BaseRetrofitService(config), AuthService {

    private val authApi = retrofit.create(AuthAPI::class.java)

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