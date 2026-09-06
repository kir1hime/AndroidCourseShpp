package com.example.androidcourseshpp.data.source.local.database.utils

import androidx.sqlite.SQLiteException
import com.example.androidcourseshpp.domain.entity.contact.SyncAction

enum class SyncState(val code: String) {
    SYNCED("SYNCED"), ADDED("ADDED"), DELETED("DELETED");

    companion object {
        fun getSyncState(code: String) = entries.find { it.code == code } ?: throw SQLiteException()
    }
}

fun SyncState.toSyncAction(): SyncAction {
    return when (this) {
        SyncState.SYNCED -> SyncAction.SYNCED
        SyncState.ADDED -> SyncAction.ADDED
        SyncState.DELETED -> SyncAction.DELETED
    }
}


fun SyncAction.toSyncState(): SyncState {
    return when (this) {
        SyncAction.SYNCED -> SyncState.SYNCED
        SyncAction.ADDED -> SyncState.ADDED
        SyncAction.DELETED -> SyncState.DELETED
    }
}