package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.data.source.local.database.repository.ContactsLocalRepositoryImpl
import com.example.androidcourseshpp.domain.repository.ContactsNetworkRepository
import com.example.androidcourseshpp.data.source.network.repository.ContactsNetworkRepositoryImpl
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.data.source.local.repository.GalleryRepositoryImpl
import com.example.androidcourseshpp.data.source.local.repository.UserLocalDataRepositoryImpl
import com.example.androidcourseshpp.data.source.network.repository.AuthRepositoryImpl
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.data.source.network.repository.UserRepositoryImpl
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.ContactsLocalRepository
import com.example.androidcourseshpp.domain.repository.UserLocalDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoriesModule {

    @Binds
    @Singleton
    fun bindContactsRepository(contactsRepositoryImpl: ContactsNetworkRepositoryImpl): ContactsNetworkRepository

    @Binds
    @Singleton
    fun bindUserRepository(usersRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    fun bindGalleryRepository(galleryRepositoryImpl: GalleryRepositoryImpl): GalleryRepository

    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindUserLocalRepository(userRepositoryImpl: UserLocalDataRepositoryImpl): UserLocalDataRepository

    @Binds
    @Singleton
    fun bindContactsLocalRepository(contactsLocalRepositoryImpl: ContactsLocalRepositoryImpl): ContactsLocalRepository

}