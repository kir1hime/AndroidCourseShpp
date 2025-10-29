package com.example.androidcourseshpp.data.network.webapi.user

import com.example.androidcourseshpp.data.network.dto.user.UpdateUserRequestDTO
import com.example.androidcourseshpp.data.network.dto.user.UpdateUserResponseDTO
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserAPI {

    @PUT("users/{userId}")
    suspend fun updateUserInfo(
        @Path("userId") userId: Long,
        @Body updateUserRequestDTO: UpdateUserRequestDTO
    ): UpdateUserResponseDTO
}