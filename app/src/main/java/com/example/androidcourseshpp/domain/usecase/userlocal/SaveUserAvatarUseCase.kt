package com.example.androidcourseshpp.domain.usecase.userlocal

import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import javax.inject.Singleton

@Singleton
class SaveUserAvatarUseCase(private val userLocalRepository: UserLocalRepository) {
    operator fun invoke(avatarUrl: String) {
        userLocalRepository.saveUserAvatarUrl(avatarUrl)
    }
}