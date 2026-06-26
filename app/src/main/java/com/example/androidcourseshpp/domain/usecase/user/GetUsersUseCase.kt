package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.user.UserListItemInfo
import com.example.androidcourseshpp.domain.repository.UserRepository


class GetUsersUseCase(val userRepository: UserRepository) {

    suspend operator fun invoke(): List<UserListItemInfo> {
        return userRepository.getUsers()
    }
}