package com.example.androidcourseshpp.data.network.repository.auth

import com.example.androidcourseshpp.data.network.RetrofitConfig
import com.example.androidcourseshpp.data.network.dto.auth.SignUpRequestDTO
import com.example.androidcourseshpp.data.network.repository.BaseRetrofitRepository
import com.example.androidcourseshpp.data.network.repository.auth.entity.SignUpData
import com.example.androidcourseshpp.data.network.webapi.auth.AuthAPI
import kotlinx.coroutines.delay


class AuthRepositoryImpl(
    config: RetrofitConfig
) : BaseRetrofitRepository(config), AuthRepository {

    private val signUpApi = retrofit.create(AuthAPI::class.java)

    override suspend fun signUp(data: SignUpData) {
        processRetrofitExceptions {
            val signUpRequestDTO = SignUpRequestDTO(
                email = data.email,
                name = data.name,
                password = data.password,
                phone = data.phone
            )
            delay(5000)

            signUpApi.signUp(signUpRequestDTO)
        }
    }
}