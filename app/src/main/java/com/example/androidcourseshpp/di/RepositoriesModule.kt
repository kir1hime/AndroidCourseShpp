package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.domain.repository.ContactsRepository
import com.example.androidcourseshpp.data.source.network.repository.ContactsRepositoryImpl
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.data.source.local.repository.GalleryRepositoryImpl
import com.example.androidcourseshpp.data.source.local.repository.UserLocalRepositoryImpl
import com.example.androidcourseshpp.data.source.network.repository.AuthRepositoryImpl
import com.example.androidcourseshpp.domain.repository.UserRepository
import com.example.androidcourseshpp.data.source.network.repository.UserRepositoryImpl
import com.example.androidcourseshpp.domain.repository.AuthRepository
import com.example.androidcourseshpp.domain.repository.UserLocalRepository
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
    fun bindContactsRepository(contactsRepositoryImpl: ContactsRepositoryImpl): ContactsRepository

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
    fun bindUserLocalRepository(userRepositoryImpl: UserLocalRepositoryImpl): UserLocalRepository

}