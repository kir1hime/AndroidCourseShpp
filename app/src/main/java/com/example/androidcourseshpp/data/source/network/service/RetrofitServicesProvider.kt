package com.example.androidcourseshpp.data.source.network.service

import com.example.androidcourseshpp.data.source.network.service.auth.AuthService
import com.example.androidcourseshpp.data.source.network.service.contacts.ContactsService
import com.example.androidcourseshpp.data.source.network.service.user.UserService
import jakarta.inject.Inject

class RetrofitServicesProvider @Inject constructor(
    private val authService: AuthService,
    private val userService: UserService,
    private val contactsService: ContactsService
) : ServicesProvider {

    override fun getAuthService() = authService
    override fun getUserService() = userService
    override fun getContactsService() = contactsService
}