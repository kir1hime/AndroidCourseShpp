package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.entity.auth.SignInInfo
import com.example.androidcourseshpp.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SignInUseCase @Inject constructor(private val authRepository: AuthRepository) {

    suspend operator fun invoke(signInInfo: SignInInfo): Int {
        return authRepository.signIn(signInInfo)
    }
}