package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserUseCase @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(userServerId: Long): UserInfo {
        val userInfo = userRepository.getUser(userServerId)
        return userInfo
    }
}