package com.example.androidcourseshpp.domain.usecase.user

import com.example.androidcourseshpp.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AddContactUseCase @Inject constructor(private val userRepository: UserRepository) {
    suspend operator fun invoke(newContactId: Int) {
        userRepository.addContact(newContactId)
    }
}