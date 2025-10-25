package com.example.androidcourseshpp.data.network.webapi.auth

import com.example.androidcourseshpp.data.network.dto.auth.SignUpRequestDTO
import com.example.androidcourseshpp.data.network.dto.auth.SignUpResponseDTO
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("users")
    suspend fun signUp(@Body signUpRequestDTO: SignUpRequestDTO): SignUpResponseDTO
}