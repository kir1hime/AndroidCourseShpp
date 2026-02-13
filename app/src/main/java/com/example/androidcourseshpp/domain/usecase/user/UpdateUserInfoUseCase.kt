package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.entity.user.UserInfo
import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import com.example.androidcourseshpp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateUserInfoUseCase @Inject constructor(
    private val userRepository: UserRepository,
    private val userLocalRepository: UserLocalRepository
) {

    suspend operator fun invoke(userInfo: UserInfo) {
        userRepository.updateUserInfo(userInfo)
        userLocalRepository.saveUserAvatarUrl(userInfo.avatar)
    }
}