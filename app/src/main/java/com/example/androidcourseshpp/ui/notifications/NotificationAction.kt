package com.example.androidcourseshpp.ui.notifications

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class NotificationAction : Parcelable {
    ADD_CONTACT, DELETE_CONTACT
}