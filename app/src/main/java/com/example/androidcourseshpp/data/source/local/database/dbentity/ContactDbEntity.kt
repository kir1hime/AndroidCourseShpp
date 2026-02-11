package com.example.androidcourseshpp.data.source.local.database.dbentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidcourseshpp.data.source.local.database.model.ContactModel

@Entity(
    tableName = "contacts"
)
data class ContactDbEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val career: String,
    @ColumnInfo("avatar_url") val avatarURL: String
) {
    fun toContactModel() =
        ContactModel(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL
        )

    fun fromContactModel(contact: ContactModel) =
        ContactDbEntity(
            id = id,
            name = name,
            career = career,
            avatarURL = avatarURL
        )
}