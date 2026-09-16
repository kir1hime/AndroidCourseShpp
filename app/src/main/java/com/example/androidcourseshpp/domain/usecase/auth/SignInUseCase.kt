package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onSuccess

class SignInUseCase (
    private val authRepository: AuthRepository,
    private val userLocalDataRepository: UserLocalDataRepository
) {

    suspend operator fun invoke(
        signInInfo: SignInInfo,
        toRememberUser: Boolean
    ): Result<UserInfo, DataError.NetworkError> {

        val userInfo = authRepository.signIn(signInInfo).onSuccess { data ->
            userLocalDataRepository.saveUserServerId(data.id)
            if (toRememberUser) {
                userLocalDataRepository.setUserRememberState(true)
            }
        }
        return userInfo
    }
}