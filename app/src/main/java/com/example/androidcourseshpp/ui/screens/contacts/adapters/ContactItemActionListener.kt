package com.example.androidcourseshpp.ui.screens.contacts.adapters

import com.example.androidcourseshpp.data.contactlistdata.ContactItem

interface ContactItemActionListener {

    fun deleteContactItem (contactItem: ContactItem)

    fun showUndoDeletingSnackBarContactItem(contactItem: ContactItem, position: Int)
}