package com.example.androidcourseshpp.data.source.network.mapper

import com.example.androidcourseshpp.data.source.network.dto.auth.SignInRequestDTO
import com.example.androidcourseshpp.data.source.network.dto.auth.SignInResponseDTO
import com.example.androidcourseshpp.data.source.network.model.auth.SignInRequestModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignInResponseModel
import com.example.androidcourseshpp.domain.entity.auth.SignInInfo

fun SignInRequestModel.toSignInRequestDTO() = SignInRequestDTO(
    email = email,
    password = password
)

fun SignInResponseDTO.toSignInResponseModel() = with(data) {
    SignInResponseModel(
        user = user.toUserModel(),
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

fun SignInInfo.toSignInRequestModel() = SignInRequestModel(
    email = email,
    password = password
)

