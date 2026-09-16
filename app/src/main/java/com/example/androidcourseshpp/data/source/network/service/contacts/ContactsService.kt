package com.example.androidcourseshpp.data.source.network.service.contacts

import com.example.androidcourseshpp.data.source.network.model.contacts.GetUserContactsResponseModel
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface ContactsService {

    suspend fun addContact(userId: Long, contactId: Long): Result<Unit, DataError.NetworkError>
    suspend fun deleteContact(userId: Long, contactId: Long): Result<Unit, DataError.NetworkError>
    suspend fun getUserContacts(userId: Long): Result<GetUserContactsResponseModel, DataError.NetworkError>
}