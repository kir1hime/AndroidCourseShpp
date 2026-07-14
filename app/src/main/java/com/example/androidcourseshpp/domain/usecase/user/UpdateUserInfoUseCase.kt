package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.domain.utils.Result

class UpdateUserInfoUseCase(
    private val userRepository: UserRepository,
    private val userLocalDataRepository: UserLocalDataRepository
) {

    suspend operator fun invoke(userInfo: UserInfo): Result<Unit> {
        val result = userRepository.updateUserInfo(userInfo)
        userLocalDataRepository.saveUserAvatarUrl(userInfo.avatar)
        return result
    }
}