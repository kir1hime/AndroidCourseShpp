package com.example.androidcourseshpp.ui.screens.userinfo.contactlist.adapters

import android.widget.ImageView
import com.example.androidcourseshpp.data.contactlistdata.ContactItem

interface ItemActions {

    fun deleteContactItem(contactItem: ContactItem, position: Int)

    fun showContactItemDetails(contactItem: ContactItem, avatar: ImageView)

    fun showFloatingDeleteButton()

    fun hideFloatingDeleteButton()
}