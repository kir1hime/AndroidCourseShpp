package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository

interface GetUserAvatarUseCase {
    suspend operator fun invoke(): String?
}

class GetUserAvatarUseCaseImpl(
    private val userLocalDataRepository: UserLocalDataRepository
) : GetUserAvatarUseCase {
    override suspend operator fun invoke(): String? {
        return userLocalDataRepository.getUserAvatarUrl()
    }
}