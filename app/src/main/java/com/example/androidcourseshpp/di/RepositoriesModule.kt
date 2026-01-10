package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.domain.repository.ContactsRepository
import com.example.androidcourseshpp.data.source.network.repository.ContactsRepositoryImpl
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.data.source.local.repository.GalleryRepositoryImpl
import com.example.androidcourseshpp.data.source.local.repository.UserRepositoryImpl
import com.example.androidcourseshpp.data.source.network.repository.AuthRepositoryImpl
import com.example.androidcourseshpp.domain.repository.UsersRepository
import com.example.androidcourseshpp.data.source.network.repository.UsersRepositoryImpl
import com.example.androidcourseshpp.domain.repository.AuthRepository
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
    fun bindUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository

    @Binds
    @Singleton
    fun bindGalleryRepository(galleryRepositoryImpl: GalleryRepositoryImpl): GalleryRepository

    @Binds
    @Singleton
    fun bindAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    fun bindUserRepository(userRepositoryImpl: UserRepositoryImpl): UsersRepository

}