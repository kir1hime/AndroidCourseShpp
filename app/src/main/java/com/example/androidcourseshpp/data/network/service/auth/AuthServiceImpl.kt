package com.example.androidcourseshpp.data.network.service.auth

import com.example.androidcourseshpp.data.network.RetrofitConfig
import com.example.androidcourseshpp.data.network.dto.auth.SignUpRequestDTO
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService
import com.example.androidcourseshpp.data.network.service.auth.entity.SignUpData
import com.example.androidcourseshpp.data.network.webapi.auth.AuthAPI


class AuthServiceImpl(
    config: RetrofitConfig
) : BaseRetrofitService(config), AuthService {

    private val signUpApi = retrofit.create(AuthAPI::class.java)

    override suspend fun signUp(data: SignUpData) =
        processRetrofitExceptions {
            val signUpRequestDTO = SignUpRequestDTO(
                email = data.email,
                password = data.password,
                name = data.userName,
                phone = data.mobilePhone
            )
            signUpApi.signUp(signUpRequestDTO).data
        }
}