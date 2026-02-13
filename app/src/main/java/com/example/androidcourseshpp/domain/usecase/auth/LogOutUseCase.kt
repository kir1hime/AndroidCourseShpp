package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.domain.repository.UserLocalRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userLocalRepository: UserLocalRepository,
    private val galleryRepository: GalleryRepository
) {
    operator fun invoke() {
        authRepository.clearTokens()
        userLocalRepository.clearUserServerId()
        userLocalRepository.clearUserAvatarUrl()
        galleryRepository.clearGalleryPhotos()
    }
}