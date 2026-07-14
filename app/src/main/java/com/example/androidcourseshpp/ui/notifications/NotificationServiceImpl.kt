package com.example.androidcourseshpp.ui.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.ui.NOTIFICATION_CONTACTS_CHANNEL_ID
import com.example.androidcourseshpp.ui.screens.model.ContactDetailsModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NotificationServiceImpl @Inject constructor(@ApplicationContext private val context: Context) :
    NotificationService {

    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun showContactAddedNotification(
        userInfo: ContactDetailsModel,
        notificationActionId: Int
    ) {
        createNotification(
            title = context.getString(R.string.add_contact_notif_title),
            content = context.getString(R.string.add_contact_notif_content, userInfo.name),
            icon = R.drawable.ic_contact_added_notification,
            notificationId = CONTACT_ADDED_NOTIFICATION_ID,
            link = with(userInfo) { "notification://user_details?name=$name&career=$career&address=$address&avatarURL=$avatarURL&notifId=$notificationActionId" }

        )
    }

    override fun showContactDeletedNotification(
        userInfo: ContactDetailsModel,
        notificationActionId: Int
    ) {
        createNotification(
            title = context.getString(R.string.delete_contact_notif_title),
            content = context.getString(R.string.delete_contact_notif_content, userInfo.name),
            icon = R.drawable.ic_contact_removed_notification,
            notificationId = CONTACT_DELETED_NOTIFICATION_ID,
            link = with(userInfo) { "notification://user_details?name=$name&career=$career&address=$address&avatarURL=$avatarURL&notifId=$notificationActionId" }
        )
    }

    private fun createNotification(
        title: String,
        content: String,
        @DrawableRes icon: Int,
        notificationId: Int,
        link: String
    ) {
        val activityIntent = Intent(Intent.ACTION_VIEW, link.toUri()).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        val activityPendingIntent = PendingIntent.getActivity(
            context,
            PENDING_INTENT_REQUEST_CODE,
            activityIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, NOTIFICATION_CONTACTS_CHANNEL_ID)
            .setSmallIcon(icon)
            .setContentTitle(title)
            .setContentText(content)
            .addAction(
                icon,
                context.getString(R.string.view_details_label),
                activityPendingIntent
            )
            .build()

        notificationManager.notify(
            notificationId, notification
        )
    }

    companion object {
        const val CONTACT_ADDED_NOTIFICATION_ID = 1
        const val CONTACT_DELETED_NOTIFICATION_ID = 2
        const val PENDING_INTENT_REQUEST_CODE = 0
    }

}
