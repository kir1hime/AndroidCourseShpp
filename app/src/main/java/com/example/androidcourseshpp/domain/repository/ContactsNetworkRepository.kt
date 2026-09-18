package com.example.androidcourseshpp.domain.repository

import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface ContactsNetworkRepository {
    suspend fun addContacts(contactsIds: List<Long>): Result<Unit, DataError.NetworkError>
    suspend fun deleteContacts(contactsIds: List<Long>): Result<Unit, DataError.NetworkError>
    suspend fun getContacts(): Result<List<ContactInfo>, DataError.NetworkError>
}