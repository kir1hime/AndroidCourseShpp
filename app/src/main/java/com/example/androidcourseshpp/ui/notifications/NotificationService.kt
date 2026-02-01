package com.example.androidcourseshpp.ui.notifications


interface NotificationService {
    fun showContactAddedNotification(contactName: String, userId: Int, notificationActionId: Int)
    fun showContactDeletedNotification(contactName: String, userId: Int, notificationActionId: Int)
}