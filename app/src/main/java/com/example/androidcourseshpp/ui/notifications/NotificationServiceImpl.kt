package com.example.androidcourseshpp.ui.notifications

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

    override fun showContactAddedNotification(newUserName: String) {
        createNotification(
            title = context.getString(R.string.add_contact_notif_title),
            content = context.getString(R.string.add_contact_notif_content, newUserName),
            icon = R.drawable.ic_contact_added_notification
        )
    }

    override fun showContactRemovedNotification(deletedUserName: String) {
        createNotification(
            title = context.getString(R.string.delete_contact_notif_title),
            content = context.getString(R.string.delete_contact_notif_content, deletedUserName),
            icon = R.drawable.ic_contact_removed_notification
        )
    }

    private fun createNotification(title: String, content: String, @DrawableRes icon: Int) {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_CONTACTS_CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(content)
            .build()

    }


}
