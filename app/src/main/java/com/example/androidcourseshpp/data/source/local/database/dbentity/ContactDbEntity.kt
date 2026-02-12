package com.example.androidcourseshpp.data.source.local.database.dbentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo

@Entity(
    tableName = "contacts"
)
data class ContactDbEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val career: String,
    val address: String,
    @ColumnInfo("avatar_url") val avatarURL: String
) {
    fun toContactInfo() =
        ContactInfo(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL,
            address = address
        )

    companion object {
        fun fromContactInfo(contact: ContactInfo) =
            ContactDbEntity(
                id = contact.id,
                name = contact.name,
                career = contact.career,
                avatarURL = contact.avatarURL,
                address = contact.address
            )
    }
}