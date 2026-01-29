package com.example.androidcourseshpp.ui.notifications


interface NotificationService {
    fun showContactAddedNotification(newUserName: String)
    fun showContactRemovedNotification(deletedUserName: String)
}