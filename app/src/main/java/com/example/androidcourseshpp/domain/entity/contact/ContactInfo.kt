package com.example.androidcourseshpp.domain.entity.contact

data class ContactInfo(
    val id: Int,
    val name: String,
    val career: String,
    val address: String,
    val avatarURL: String
)

data class SyncContactInfo(val contactInfo: ContactInfo, val syncState: SyncAction)

enum class SyncAction {
    SYNCED,
    ADDED,
    DELETED
}