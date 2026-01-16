package com.example.androidcourseshpp.domain.usecase.userlocal

import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearUserAvatarUseCase @Inject constructor(private val userLocalRepository: UserLocalRepository) {
    operator fun invoke() {
        userLocalRepository.clearUserAvatarUrl()
    }
}