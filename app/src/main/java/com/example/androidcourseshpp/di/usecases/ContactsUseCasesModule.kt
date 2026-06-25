package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.ContactsRepository
import com.example.androidcourseshpp.domain.usecase.contacts.AddContactUseCase
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
    fun provideAddContactUseCase(contactsRepository: ContactsRepository) =
        AddContactUseCase(contactsRepository)

    @Singleton
    @Provides
    fun provideDeleteContactUseCase(contactsRepository: ContactsRepository) =
        DeleteContactUseCase(contactsRepository)

    @Singleton
    @Provides
    fun provideDeleteContactsUseCase(contactsRepository: ContactsRepository) =
        DeleteContactsUseCase(contactsRepository)

    @Singleton
    @Provides
    fun provideGetContactsUseCase(contactsRepository: ContactsRepository) =
        GetContactsUseCase(contactsRepository)
}