package com.example.androidcourseshpp.ui.notifications


interface NotificationService {
    fun showContactAddedNotification(contactName: String, contactId: Int)
    fun showContactDeletedNotification(contactName: String, contactId: Int)
}