package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

class GetUserUseCase(private val userRepository: UserRepository) {

    suspend operator fun invoke(): Result<UserInfo, DataError.NetworkError> {
        val userInfo = userRepository.getUser()
        return userInfo
    }
}