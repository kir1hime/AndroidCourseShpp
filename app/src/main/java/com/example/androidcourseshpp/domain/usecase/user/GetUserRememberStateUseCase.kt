package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository

interface GetUserRememberStateUseCase {
    suspend operator fun invoke(): Boolean?
}

class GetUserRememberStateUseCaseImpl(
    private val userLocalDataRepository: UserLocalDataRepository
) : GetUserRememberStateUseCase {

    override suspend operator fun invoke(): Boolean? {
        return userLocalDataRepository.isUserRemembered()
    }
}