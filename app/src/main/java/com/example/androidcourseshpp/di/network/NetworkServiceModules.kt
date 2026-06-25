package com.example.androidcourseshpp.di.network

import com.example.androidcourseshpp.data.source.network.service.auth.AuthService
import com.example.androidcourseshpp.data.source.network.service.auth.AuthServiceImpl
import com.example.androidcourseshpp.data.source.network.service.contacts.ContactsService
import com.example.androidcourseshpp.data.source.network.service.contacts.ContactsServiceImpl
import com.example.androidcourseshpp.data.source.network.service.user.UserService
import com.example.androidcourseshpp.data.source.network.service.user.UserServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface NetworkServiceModules {

    @Binds
    fun bindAuthService(authServiceImpl: AuthServiceImpl): AuthService

    @Binds
    fun bindUserService(userServiceImpl: UserServiceImpl): UserService
    @Binds
    fun bindContactService(contactsServiceImpl: ContactsServiceImpl) : ContactsService
}