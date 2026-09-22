package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactsUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactsUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactsUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.DeleteContactsUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.contacts.GetContactsUseCase
import com.example.androidcourseshpp.domain.usecase.contacts.GetContactsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ContactUseCasesProvideModule {

    @Singleton
    @Provides
    fun provideAddContactUseCase(contactsLocalRepository: ContactsLocalRepository) =
        AddContactUseCaseImpl(contactsLocalRepository = contactsLocalRepository)

    @Singleton
    @Provides
    fun provideAddContactsUseCase(contactsLocalRepository: ContactsLocalRepository) =
        AddContactsUseCaseImpl(contactsLocalRepository = contactsLocalRepository)

    @Singleton
    @Provides
    fun provideDeleteContactUseCase(contactsLocalRepository: ContactsLocalRepository) =
        DeleteContactUseCaseImpl(contactsLocalRepository = contactsLocalRepository)

    @Singleton
    @Provides
    fun provideDeleteContactsUseCase(contactsLocalRepository: ContactsLocalRepository) =
        DeleteContactsUseCaseImpl(contactsLocalRepository = contactsLocalRepository)

    @Singleton
    @Provides
    fun provideGetContactsUseCase(
        contactsLocalRepository: ContactsLocalRepository
    ) = GetContactsUseCaseImpl(
        contactsLocalRepository = contactsLocalRepository,
    )
}

@Module
@InstallIn(SingletonComponent::class)
interface ContactUseCasesBindModule {

    @Binds
    fun bindAddContactUseCase(
        addContactUseCaseImpl: AddContactUseCaseImpl
    ): AddContactUseCase

    @Binds
    fun bindAddContactsUseCase(
        addContactsUseCaseImpl: AddContactsUseCaseImpl
    ): AddContactsUseCase

    @Binds
    fun bindDeleteContactUseCase(
        deleteContactUseCaseImpl: DeleteContactUseCaseImpl
    ): DeleteContactUseCase

    @Binds
    fun bindDeleteContactsUseCase(
        deleteContactsUseCaseImpl: DeleteContactsUseCaseImpl
    ): DeleteContactsUseCase

    @Binds
    fun bindGetContactsUseCases(
        getContactsUseCaseImpl: GetContactsUseCaseImpl
    ): GetContactsUseCase
}