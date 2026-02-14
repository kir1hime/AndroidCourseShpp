package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import com.example.androidcourseshpp.domain.utils.Result
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SignInUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userLocalRepository: UserLocalRepository
) {

    operator fun invoke(
        signInInfo: SignInInfo,
        toRememberUser: Boolean
    ): Flow<Result<UserInfo>> = flow {


        val userInfo = authRepository.signIn(signInInfo)
        if (toRememberUser) {
            userLocalRepository.saveUserServerId(userInfo.id)
        }
        emit(Result.Success(userInfo))


    }
}