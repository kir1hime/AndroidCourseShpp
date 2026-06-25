package com.example.androidcourseshpp.domain.usecase.userlocal

import com.example.androidcourseshpp.domain.repository.UserLocalRepository


class GetUserAvatarUseCase(private val userLocalRepository: UserLocalRepository) {
    operator fun invoke(): String {
        return userLocalRepository.getUserAvatarUrl()
    }
}