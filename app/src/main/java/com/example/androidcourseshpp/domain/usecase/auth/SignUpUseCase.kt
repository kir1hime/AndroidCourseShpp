package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.entity.auth.SignUpInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onSuccess

class SignUpUseCase(
    private val authRepository: AuthRepository,
    private val userLocalDataRepository: UserLocalDataRepository
) {

    suspend operator fun invoke(signUpInfo: SignUpInfo, toRememberUser: Boolean): Result<UserInfo> {
        val userInfo = authRepository.singUp(signUpInfo).onSuccess { data ->
            userLocalDataRepository.saveUserServerId(data.id)
            if (toRememberUser) {
                userLocalDataRepository.setUserRememberState(true)
            }
        }
        return userInfo
    }
}