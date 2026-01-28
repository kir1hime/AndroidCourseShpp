package com.example.androidcourseshpp.ui.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.ui.NOTIFICATION_CONTACTS_CHANNEL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationServiceImpl @Inject constructor(@ApplicationContext private val context: Context) :
    NotificationService {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showContactAddedNotification() {
        createNotification(
            "Add Contact",
            "New contact was added",
            R.drawable.ic_contact_added_notification
        )
    }

    override fun showContactRemovedNotification() {

    }

    private fun createNotification(title: String, content: String, @DrawableRes icon: Int) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CONTACTS_CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(content)
            .build()

        notificationManager.notify(
            1, notification
        )
    }
}
