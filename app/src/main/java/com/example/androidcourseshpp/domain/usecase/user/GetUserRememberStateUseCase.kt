package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository

interface GetUserRememberStateUseCase {
    operator fun invoke(): Boolean
}

class GetUserRememberStateUseCaseImpl(
    private val userLocalDataRepository: UserLocalDataRepository
) : GetUserRememberStateUseCase {

    override operator fun invoke(): Boolean {
        return userLocalDataRepository.isUserRemembered()
    }
}