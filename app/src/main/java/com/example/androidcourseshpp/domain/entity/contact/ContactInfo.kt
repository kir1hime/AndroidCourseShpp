package com.example.androidcourseshpp.domain.entity.contact

import com.example.androidcourseshpp.domain.entity.sync.SyncStatus


data class ContactInfo(
    val id: Long,
    val name: String,
    val career: String,
    val address: String,
    val avatarURL: String,
)

data class SyncContactInfo(val contactInfo: ContactInfo, val syncStatus: SyncStatus)


