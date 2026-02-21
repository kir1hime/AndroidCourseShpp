package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserServerIdUseCase @Inject constructor(private val userLocalDataRepository: UserLocalDataRepository) {
    operator fun invoke(): Int {
        return userLocalDataRepository.getUserServerId()
    }
}