package com.example.androidcourseshpp.data.network.webapi.auth

import com.example.androidcourseshpp.data.network.dto.auth.SignInRequestDTO
import com.example.androidcourseshpp.data.network.dto.auth.SignInResponseDTO
import com.example.androidcourseshpp.data.network.dto.auth.SignUpResponseDTO
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface AuthAPI {

    @Multipart
    @POST("users")
    suspend fun signUp(
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("name") name: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part image: MultipartBody.Part?
    ): SignUpResponseDTO

    @POST("login")
    suspend fun singIn(@Body singInRequestDTO: SignInRequestDTO): SignInResponseDTO
}