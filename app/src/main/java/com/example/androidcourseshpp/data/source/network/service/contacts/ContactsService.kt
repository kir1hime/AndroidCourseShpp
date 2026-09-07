package com.example.androidcourseshpp.data.source.network.service.contacts

import com.example.androidcourseshpp.data.source.network.model.contacts.ContactRequestModel
import com.example.androidcourseshpp.data.source.network.model.contacts.GetUserContactsResponseModel
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface ContactsService {

    suspend fun addContact(data: ContactRequestModel): Result<Unit, DataError.Network>
    suspend fun deleteContact(data: ContactRequestModel): Result<Unit, DataError.Network>
    suspend fun getUserContacts(userId: Long): Result<GetUserContactsResponseModel, DataError.Network>
}