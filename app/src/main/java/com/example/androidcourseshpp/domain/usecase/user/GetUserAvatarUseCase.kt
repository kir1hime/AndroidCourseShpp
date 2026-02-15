package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserAvatarUseCase @Inject constructor(private val userLocalRepository: UserLocalRepository) {
    operator fun invoke(): String {
        return userLocalRepository.getUserAvatarUrl()
    }
}