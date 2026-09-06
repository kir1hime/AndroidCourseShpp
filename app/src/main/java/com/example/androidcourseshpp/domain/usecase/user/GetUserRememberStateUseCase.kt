package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository


class GetUserRememberStateUseCase (
    private val userLocalDataRepository: UserLocalDataRepository
) {

    operator fun invoke(): Boolean {
        return userLocalDataRepository.isUserRemembered()
    }
}