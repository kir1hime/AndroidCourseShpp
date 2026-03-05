package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserRememberStateUseCase @Inject constructor(
    private val userLocalDataRepository: UserLocalDataRepository
) {

    operator fun invoke(): Boolean {
        return userLocalDataRepository.isUserRemembered()
    }
}