package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.data.models.contactlist.ContactsRepository
import com.example.androidcourseshpp.data.models.contactlist.ContactsRepositoryImpl
import com.example.androidcourseshpp.data.models.gallery.GalleryRepository
import com.example.androidcourseshpp.data.models.gallery.GalleryRepositoryImpl
import com.example.androidcourseshpp.data.models.userlist.UsersRepository
import com.example.androidcourseshpp.data.models.userlist.UsersRepositoryImpl
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