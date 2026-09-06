package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
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
    fun provideAddContactsUseCase(addContactUseCase: AddContactUseCase) =
        AddContactsUseCase(addContactUseCase = addContactUseCase)

    @Singleton
    @Provides
    fun provideDeleteContactUseCase(contactsLocalRepository: ContactsLocalRepository) =
        DeleteContactUseCase(contactsLocalRepository = contactsLocalRepository)

    @Singleton
    @Provides
    fun provideDeleteContactsUseCase(deleteContactUseCase: DeleteContactUseCase) =
        DeleteContactsUseCase(deleteContactUseCase = deleteContactUseCase)

    @Singleton
    @Provides
    fun provideGetContactsUseCase(
        contactsLocalRepository: ContactsLocalRepository,
        contactsNetworkRepository: ContactsNetworkRepository
    ) = GetContactsUseCase(
        contactsLocalRepository = contactsLocalRepository,
        contactsNetworkRepository = contactsNetworkRepository
    )
}