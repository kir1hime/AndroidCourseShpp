package com.example.androidcourseshpp.data.source.network.mapper

import com.example.androidcourseshpp.data.source.network.dto.common.UserDTO
import com.example.androidcourseshpp.data.source.network.dto.user.GetUserResponseDTO
import com.example.androidcourseshpp.data.source.network.dto.user.GetUsersResponseDTO
import com.example.androidcourseshpp.data.source.network.dto.user.UpdateUserRequestDTO
import com.example.androidcourseshpp.data.source.network.model.common.UserModel
import com.example.androidcourseshpp.data.source.network.model.user.GetUserResponseModel
import com.example.androidcourseshpp.data.source.network.model.user.GetUsersResponseModel
import com.example.androidcourseshpp.data.source.network.model.user.UpdateUserRequestModel
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo

fun UserDTO.toUserModel() = UserModel(
    id = id,
    email = email,
    name = name,
    phone = phone,
    address = address,
    career = career,
    birthday = birthday,
    facebook = facebook,
    instagram = instagram,
    twitter = twitter,
    linkedin = linkedin,
    image = image
)

fun GetUserResponseDTO.toGetUserResponseModel() = with(data) {
    GetUserResponseModel(
        user = user.toUserModel()
    )
}

fun GetUsersResponseDTO.toGetUsersResponseModel() = with(data) {
    GetUsersResponseModel(
        users = users.map { it.toUserModel() }
    )
}

fun UpdateUserRequestModel.toUpdateUserRequestDTO() =
    UpdateUserRequestDTO(
        name = name,
        phone = phone,
        address = address,
        career = career,
        birthday = birthday,
        facebook = facebook,
        instagram = instagram,
        twitter = twitter,
        linkedin = linkedin
    )


fun UserModel.toUserInfo() =
    UserInfo(
        id = id,
        name = name ?: "",
        mobilePhone = phone ?: "",
        address = address ?: "",
        career = career ?: "",
        avatar = image ?: "",
        dateOfBirthday = birthday
    )

fun UserModel.toContactInfo() =
    ContactInfo(
        id = id,
        name = name ?: "",
        career = career ?: "",
        address = address ?: "",
        avatarURL = image ?: ""
    )

fun UserInfo.toUpdateUserModel() =
    UpdateUserRequestModel(
        id = id,
        name = name.takeIf { it.isNotBlank() },
        phone = mobilePhone.takeIf { it.isNotBlank() },
        career = career.takeIf { it.isNotBlank() },
        address = address.takeIf { it.isNotBlank() },
        birthday = dateOfBirthday,
        image = avatar.takeIf { it.isNotBlank() }
    )