package com.example.androidcourseshpp.domain.usecase.auth

import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LogOutUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val userLocalDataRepository: UserLocalDataRepository,
    private val galleryRepository: GalleryRepository,
    private val contactsLocalRepository: ContactsLocalRepository
) {
    suspend operator fun invoke() {
        authRepository.clearTokens()
        userLocalDataRepository.clearUserServerId()
        userLocalDataRepository.clearUserAvatarUrl()
        userLocalDataRepository.setUserRememberState(false)
        galleryRepository.clearGalleryPhotos()
        contactsLocalRepository.clearContacts()
        contactsLocalRepository.setDatabaseSynced(false)
    }
}