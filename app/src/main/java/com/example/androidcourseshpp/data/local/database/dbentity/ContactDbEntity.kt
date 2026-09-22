package com.example.androidcourseshpp.data.local.database.dbentity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.androidcourseshpp.data.local.database.utils.SyncState
import com.example.androidcourseshpp.data.local.database.utils.toSyncAction
import com.example.androidcourseshpp.data.local.database.utils.toSyncState
import com.example.androidcourseshpp.domain.entity.contact.ContactInfo
import com.example.androidcourseshpp.domain.entity.contact.SyncContactInfo

@Entity(
    tableName = "contacts"
)
data class ContactDbEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val career: String,
    val address: String,
    @ColumnInfo("avatar_url") val avatarURL: String,
    @ColumnInfo("sync_state") val syncState: SyncState
) {
    fun toSyncContactInfo() =
        SyncContactInfo(
            contactInfo = this.toContactInfo(),
            syncStatus = syncState.toSyncAction()
        )

    private fun toContactInfo() = ContactInfo(
        id = id,
        name = name,
        career = career,
        avatarURL = avatarURL,
        address = address
    )
}

fun SyncContactInfo.toContactDBEntity() =
    ContactDbEntity(
        id = contactInfo.id,
        name = contactInfo.name,
        career = contactInfo.career,
        address = contactInfo.address,
        syncState = syncStatus.toSyncState(),
        avatarURL = contactInfo.avatarURL
    )
