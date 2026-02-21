package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import com.example.androidcourseshpp.domain.utils.Result
import com.example.androidcourseshpp.domain.utils.onSuccess
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userLocalDataRepository: UserLocalDataRepository
) {

    suspend operator fun invoke(
        signInInfo: SignInInfo,
        toRememberUser: Boolean
    ): Result<UserInfo> {

        val userInfo = authRepository.signIn(signInInfo).onSuccess { data ->
            if (toRememberUser) {
                userLocalDataRepository.saveUserServerId(data.id)
            }
        }
        return userInfo
    }
}