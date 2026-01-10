package com.example.androidcourseshpp.data.source.network.entity.user

import com.example.androidcourseshpp.data.source.network.dto.user.UpdateUserRequestDTO
import java.util.Date

data class UpdateUserData(
    val name: String? = null,
    val phone: String? = null,
    val address: String? = null,
    val career: String? = null,
    val birthday: Date? = null,
    val facebook: String? = null,
    val instagram: String? = null,
    val twitter: String? = null,
    val linkedin: String? = null,
    val image: String? = null
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