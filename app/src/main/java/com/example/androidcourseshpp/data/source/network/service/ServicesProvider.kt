package com.example.androidcourseshpp.data.source.network.service

import com.example.androidcourseshpp.data.source.network.service.auth.AuthService
import com.example.androidcourseshpp.data.source.network.service.contacts.ContactsService
import com.example.androidcourseshpp.data.source.network.service.user.UserService


interface ServicesProvider {

    fun getAuthService(): AuthService
    fun getUserService(): UserService
    fun getContactsService(): ContactsService
}