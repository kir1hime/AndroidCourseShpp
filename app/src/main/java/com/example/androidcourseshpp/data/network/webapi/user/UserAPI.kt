package com.example.androidcourseshpp.data.network.webapi.user

import com.example.androidcourseshpp.data.network.dto.user.GetUserResponseDTO
import com.example.androidcourseshpp.data.network.dto.user.UpdateUserRequestDTO
import com.example.androidcourseshpp.data.network.dto.user.UpdateUserResponseDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI {

    @PUT("users/{userId}")
    suspend fun updateUserInfo(
        @Path("userId") userId: Long,
        @Body updateUserRequestDTO: UpdateUserRequestDTO
    ): UpdateUserResponseDTO

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Long): GetUserResponseDTO
}