package com.example.androidcourseshpp.ui.notifications


interface NotificationService {
    fun showContactAddedNotification(newUserName: String)
    fun showContactDeletedNotification(deletedUserName: String)
}