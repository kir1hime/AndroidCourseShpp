package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.user.UserListItemInfo
import com.example.androidcourseshpp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUsersUseCase @Inject constructor(val userRepository: UserRepository) {

    suspend operator fun invoke(): List<UserListItemInfo> {
        return userRepository.getUsers()
    }
}