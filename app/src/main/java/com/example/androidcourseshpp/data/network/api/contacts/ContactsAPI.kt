package com.example.androidcourseshpp.data.network.api.contacts

import com.example.androidcourseshpp.data.network.dto.contacts.AddContactRequestDTO
import com.example.androidcourseshpp.data.network.dto.contacts.GetUserContactsResponseDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface ContactsAPI {

    @PUT("users/{userId}/contacts")
    suspend fun addContact(@Path("userId") userId: Long, @Body addContactRequestDTO: AddContactRequestDTO)

    @DELETE("users/{userId}/contacts/{contactId}")
    suspend fun deleteContact(@Path("userId") userId: Long, @Path("contactId") contactId: Long)

    @GET("users/{userId}/contacts")
    suspend fun getUserContacts(@Path("userId") userId: Long): GetUserContactsResponseDTO
}