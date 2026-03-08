package com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.adapter

import android.widget.ImageView
import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.model.ContactItem

interface ContactItemActions {

    fun deleteContactItem(contactItem: ContactItem, position: Int)

    fun showContactItemDetails(contactItem: ContactItem, avatar: ImageView)

    /*fun showFloatingDeleteButton()

    fun hideFloatingDeleteButton()

    fun showCancelSelectTextView()*/

    fun selectModeChangingListener(isSelectMode: Boolean)
}