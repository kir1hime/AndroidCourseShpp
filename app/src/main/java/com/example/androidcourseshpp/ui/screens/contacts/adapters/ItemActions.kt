package com.example.androidcourseshpp.ui.screens.contacts.adapters

import com.example.androidcourseshpp.data.contactlistdata.ContactItem

interface ItemActions {

    fun deleteContactItem(contactItem: ContactItem, position: Int)

    fun showContactItemDetails()
}