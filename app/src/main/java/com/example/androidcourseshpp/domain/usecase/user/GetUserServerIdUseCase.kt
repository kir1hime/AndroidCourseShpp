package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserRepository

class GetUserServerIdUseCase(private val userRepository: UserRepository) {
    operator fun invoke() : Int{
       return userRepository.getUserServerId()
    }
}