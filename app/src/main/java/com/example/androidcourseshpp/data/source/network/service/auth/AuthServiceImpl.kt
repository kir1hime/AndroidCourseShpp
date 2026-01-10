package com.example.androidcourseshpp.data.source.network.service.auth

import com.example.androidcourseshpp.data.source.network.RetrofitConfig
import com.example.androidcourseshpp.data.source.network.dto.auth.SignInRequestDTO
import com.example.androidcourseshpp.data.source.network.service.BaseRetrofitService
import com.example.androidcourseshpp.data.source.network.entity.auth.SignInData
import com.example.androidcourseshpp.data.source.network.entity.auth.SignUpData
import com.example.androidcourseshpp.data.source.network.api.auth.AuthAPI
import okhttp3.RequestBody.Companion.toRequestBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthServiceImpl @Inject constructor(
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