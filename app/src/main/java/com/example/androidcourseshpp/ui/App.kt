package com.example.androidcourseshpp.ui

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.androidcourseshpp.R
import dagger.hilt.android.HiltAndroidApp

const val NOTIFICATION_CONTACTS_CHANNEL_ID = "contactsManagementChannel"
const val NOTIFICATION_CONTACTS_CHANNEL_NAME = "Contacts"
@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CONTACTS_CHANNEL_ID,
                NOTIFICATION_CONTACTS_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = getString(R.string.channel_description)
            }

            val notificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
