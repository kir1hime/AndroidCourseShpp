package com.example.androidcourseshpp.domain.usecase.userlocal

import com.example.androidcourseshpp.domain.repository.UserLocalRepository


class GetUserServerIdUseCase(private val userLocalRepository: UserLocalRepository) {
    operator fun invoke(): Long {
        return userLocalRepository.getUserServerId()
    }
}