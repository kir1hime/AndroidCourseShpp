package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.ui.screens.main.userinfo.contactlist.entity.ContactItem

interface ContactsRepository {

    suspend fun addContactItem(contactItem: ContactItem)

    suspend fun deleteContactItem(contactItem: ContactItem)

    suspend fun deleteContactItems(contactItems: List<ContactItem>)
    suspend fun loadContacts(): List<ContactItem>
}