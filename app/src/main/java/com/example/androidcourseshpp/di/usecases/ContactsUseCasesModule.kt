package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactsUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactsUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.GetContactsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContactsUseCasesModule {

    @Singleton
    @Provides
    fun provideAddContactUseCase(contactsLocalRepository: ContactsLocalRepository) =
        AddContactUseCase(contactsLocalRepository = contactsLocalRepository)

    @Singleton
    @Provides
    fun provideAddContactsUseCase(contactsLocalRepository: ContactsLocalRepository) =
        AddContactsUseCase(contactsLocalRepository = contactsLocalRepository)

    @Singleton
    @Provides
    fun provideDeleteContactUseCase(contactsLocalRepository: ContactsLocalRepository) =
        DeleteContactUseCase(contactsLocalRepository = contactsLocalRepository)

    @Singleton
    @Provides
    fun provideDeleteContactsUseCase(contactsLocalRepository: ContactsLocalRepository) =
        DeleteContactsUseCase(contactsLocalRepository = contactsLocalRepository)

    @Singleton
    @Provides
    fun provideGetContactsUseCase(
        contactsLocalRepository: ContactsLocalRepository
    ) = GetContactsUseCase(
        contactsLocalRepository = contactsLocalRepository,
    )
}