package com.example.androidcourseshpp.domain.usecase.userlocal

import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import javax.inject.Singleton

@Singleton
class ClearUserAvatarUseCase(private val userLocalRepository: UserLocalRepository) {
    operator fun invoke() {
        userLocalRepository.clearUserAvatarUrl()
    }
}