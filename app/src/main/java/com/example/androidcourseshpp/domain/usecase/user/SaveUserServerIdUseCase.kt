package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserRepository

class SaveUserServerIdUseCase(private val userRepository: UserRepository) {

    operator fun invoke(userServerId: Int) {
        userRepository.saveUserServerId(userServerId)
    }
}