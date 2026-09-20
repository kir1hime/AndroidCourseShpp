package com.example.androidcourseshpp.di.usecases

import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsFromRemoteUseCase
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsFromRemoteUseCaseImpl
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsToRemoteUseCase
import com.example.androidcourseshpp.domain.usecase.sync.SyncContactsToRemoteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SyncUseCasesProvideModule {

    @Provides
    @Singleton
    fun provideSyncContactsFromRemoteUseCase(
        contactsLocalRepository: ContactsLocalRepository,
        contactsNetworkRepository: ContactsNetworkRepository
    ) = SyncContactsFromRemoteUseCaseImpl(
        contactsLocalRepository = contactsLocalRepository,
        contactsNetworkRepository = contactsNetworkRepository
    )

    @Provides
    @Singleton
    fun provideSyncContactsToRemoteUseCase(
        contactsLocalRepository: ContactsLocalRepository,
        contactsNetworkRepository: ContactsNetworkRepository
    ) = SyncContactsToRemoteUseCaseImpl(
        contactsLocalRepository = contactsLocalRepository,
        contactsNetworkRepository = contactsNetworkRepository
    )
}

@Module
@InstallIn(SingletonComponent::class)
interface SyncUseCasesBindModule {

    @Binds
    fun bindSyncContactsFromRemoteUseCase(
        syncContactsFromRemoteUseCaseImpl: SyncContactsFromRemoteUseCaseImpl
    ): SyncContactsFromRemoteUseCase

    @Binds
    fun bindSynContactsToRemoteUseCase(
        syncContactsToRemoteUseCaseImpl: SyncContactsToRemoteUseCaseImpl
    ): SyncContactsToRemoteUseCase
}