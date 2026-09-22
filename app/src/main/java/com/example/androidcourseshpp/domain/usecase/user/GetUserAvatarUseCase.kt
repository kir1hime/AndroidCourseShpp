package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository

interface GetUserAvatarUseCase {
    operator fun invoke(): String
}

class GetUserAvatarUseCaseImpl(
    private val userLocalDataRepository: UserLocalDataRepository
) : GetUserAvatarUseCase {
    override operator fun invoke(): String {
        return userLocalDataRepository.getUserAvatarUrl()
    }
}