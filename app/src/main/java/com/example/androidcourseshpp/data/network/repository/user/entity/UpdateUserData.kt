package com.example.androidcourseshpp.data.network.repository.user.entity

import com.example.androidcourseshpp.data.network.dto.user.UpdateUserRequestDTO

data class UpdateUserData(
    val name: String?,
    val phone: String?,
    val address: String?,
    val career: String?,
    val birthday: String?,
    val facebook: String?,
    val instagram: String?,
    val twitter: String?,
    val linkedin: String?
) {
    fun toUpdateUserDataDTO() = UpdateUserRequestDTO(
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
}