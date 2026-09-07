package com.example.androidcourseshpp.data.source.network.mapper

import com.example.androidcourseshpp.data.source.network.dto.auth.SignUpResponseDTO
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpRequestModel
import com.example.androidcourseshpp.data.source.network.model.auth.SignUpResponseModel
import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo
import com.example.androidcourseshpp.ui.utils.imageconvertor.ImageConverter

fun SignUpResponseDTO.toSignUpResponseModel() = with(data) {
    SignUpResponseModel(
        user = user.toUserModel(),
        accessToken = accessToken,
        refreshToken = accessToken
    )
}

fun SignUpInfo.toSignUpRequestModel(imageConverter: ImageConverter) = SignUpRequestModel(
    email = email,
    password = password,
    userName = userName,
    mobilePhone = mobilePhone,
    image = imageConverter.convertBitmapToMultipartBody(avatar)
)