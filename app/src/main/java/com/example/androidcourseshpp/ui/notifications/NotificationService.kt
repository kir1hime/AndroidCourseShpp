package com.example.androidcourseshpp.ui.notifications

import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel


interface NotificationService {
    fun showContactAddedNotification(
        userInfo: ContactDetailsModel,
        notificationActionId: Int
    )

    fun showContactDeletedNotification(
        userInfo: ContactDetailsModel,
        notificationActionId: Int
    )
}