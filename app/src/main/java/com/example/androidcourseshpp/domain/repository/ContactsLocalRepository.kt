package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import kotlinx.coroutines.flow.Flow

interface ContactsLocalRepository {
    suspend fun addContact(contact: ContactInfo)
    suspend fun addContacts(contacts: List<ContactInfo>)
    fun getContacts(): Flow<List<ContactInfo>>
    suspend fun getContactById(id: Int): ContactInfo?
    suspend fun deleteContactById(id: Int)
    suspend fun clearContacts()
    suspend fun deleteContactsByIds(ids: List<Int>)
}