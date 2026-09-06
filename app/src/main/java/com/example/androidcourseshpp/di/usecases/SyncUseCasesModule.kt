package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsFromRemoteUseCase
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsToRemoteUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SyncUseCasesModule {

    @Provides
    @Singleton
    fun provideSyncContactsFromRemoteUseCase(
        contactsLocalRepository: ContactsLocalRepository,
        contactsNetworkRepository: ContactsNetworkRepository
    ) = SyncContactsFromRemoteUseCase(
        contactsLocalRepository = contactsLocalRepository,
        contactsNetworkRepository = contactsNetworkRepository
    )

    @Provides
    @Singleton
    fun provideSyncContactsToRemoteUseCase(
        contactsLocalRepository: ContactsLocalRepository,
        contactsNetworkRepository: ContactsNetworkRepository
    ) = SyncContactsToRemoteUseCase(
        contactsLocalRepository = contactsLocalRepository,
        contactsNetworkRepository = contactsNetworkRepository
    )
}