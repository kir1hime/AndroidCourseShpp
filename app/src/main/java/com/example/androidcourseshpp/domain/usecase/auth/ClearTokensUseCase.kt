package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ClearTokensUseCase @Inject constructor(private val authRepository: AuthRepository) {
    operator fun invoke() {
        authRepository.clearTokens()
    }
}