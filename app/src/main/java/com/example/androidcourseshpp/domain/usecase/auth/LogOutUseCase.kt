package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

class LogOutUseCase(
    private val authRepository: AuthRepository,
    private val userLocalDataRepository: UserLocalDataRepository,
    private val galleryRepository: GalleryRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {
    suspend operator fun invoke(): Result<Unit, DataError.LocalError> {
        authRepository.clearTokens()
        userLocalDataRepository.clearUserServerId()
        userLocalDataRepository.clearUserAvatarUrl()
        userLocalDataRepository.setUserRememberState(false)
        galleryRepository.clearGalleryPhotos()
        return contactsLocalRepository.clearContacts()
    }
}