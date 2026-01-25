package com.example.androidcourseshpp.di

import com.example.androidcourseshpp.ui.notifications.NotificationService
import com.example.androidcourseshpp.ui.notifications.NotificationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface NotificationsModule {

    @Binds
    @Singleton
    fun bindNotificationService(notificationServiceImpl: NotificationServiceImpl): NotificationService
}