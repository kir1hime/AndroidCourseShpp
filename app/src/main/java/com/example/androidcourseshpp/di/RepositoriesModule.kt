package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.domain.repository.ContactsRepository
import com.example.androidcourseshpp.data.source.network.repository.ContactsRepositoryImpl
import com.example.androidcourseshpp.domain.repository.GalleryRepository
import com.example.androidcourseshpp.data.source.local.repository.GalleryRepositoryImpl
import com.example.androidcourseshpp.domain.repository.UsersRepository
import com.example.androidcourseshpp.data.source.network.repository.UsersRepositoryImpl
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
    fun provideContactsRepository(contactsRepositoryImpl: ContactsRepositoryImpl): ContactsRepository

    @Binds
    @Singleton
    fun provideUsersRepository(usersRepositoryImpl: UsersRepositoryImpl): UsersRepository

    @Binds
    @Singleton
    fun provideGalleryRepository(galleryRepositoryImpl: GalleryRepositoryImpl): GalleryRepository
}