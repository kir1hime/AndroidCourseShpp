package com.example.androidcourseshpp.data.source.network.api.user

import com.example.androidcourseshpp.data.source.network.dto.user.GetUserResponseDTO
import com.example.androidcourseshpp.data.source.network.dto.user.GetUsersResponseDTO
import com.example.androidcourseshpp.data.source.network.dto.user.UpdateUserRequestDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI {

    @PUT("users/{userId}")
    suspend fun updateUserInfo(
        @Path("userId") userId: Long,
        @Body updateUserRequestDTO: UpdateUserRequestDTO
    )

    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: Long): GetUserResponseDTO

    @GET("users")
    suspend fun getUsers(): GetUsersResponseDTO
}