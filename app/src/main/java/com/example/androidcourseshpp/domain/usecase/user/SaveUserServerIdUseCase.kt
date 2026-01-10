package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SaveUserServerIdUseCase @Inject constructor(private val userRepository: UserRepository) {

    operator fun invoke(userServerId: Int) {
        userRepository.saveUserServerId(userServerId)
    }
}