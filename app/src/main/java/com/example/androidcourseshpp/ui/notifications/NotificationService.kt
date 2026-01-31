package com.example.androidcourseshpp.ui.notifications


interface NotificationService {
    fun showContactAddedNotification(contactName: String)
    fun showContactDeletedNotification(contactName: String)
}