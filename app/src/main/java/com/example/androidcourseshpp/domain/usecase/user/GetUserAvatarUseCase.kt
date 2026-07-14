package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository

class GetUserAvatarUseCase (private val userLocalDataRepository: UserLocalDataRepository) {
    operator fun invoke(): String {
        return userLocalDataRepository.getUserAvatarUrl()
    }
}