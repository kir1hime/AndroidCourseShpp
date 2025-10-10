package com.example.androidcourseshpp.data.contactlist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ContactItem(
    val id: Int,
    val name: String,
    val career: String,
    val avatarURL: String,
) : Parcelable


data class SelectableContactItem(val item: ContactItem, var isSelectionModeEnabled: Boolean)