package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.UserLocalRepository


class SignInUseCase(
    private val authRepository: AuthRepository,
    private val userLocalRepository: UserLocalRepository
) {

    suspend operator fun invoke(signInInfo: SignInInfo, toRememberUser: Boolean): UserInfo {
        val userInfo = authRepository.signIn(signInInfo)
        if (toRememberUser) {
            userLocalRepository.saveUserServerId(userInfo.id)
        }
        return userInfo
    }
}