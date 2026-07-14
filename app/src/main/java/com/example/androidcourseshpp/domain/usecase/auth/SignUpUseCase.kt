package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.UserLocalRepository

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val userLocalRepository: UserLocalRepository
) {
    suspend operator fun invoke(signUpInfo: SignUpInfo, toRememberUser: Boolean): UserInfo {
        val userInfo = authRepository.singUp(signUpInfo)
        if (toRememberUser) {
            userLocalRepository.saveUserServerId(userInfo.id)
        }
        return userInfo
    }
}