package com.example.androidcourseshpp.data.local.database.utils

import androidx.sqlite.SQLiteException
import com.example.androidcourseshpp.domain.entity.sync.SyncStatus

enum class SyncState(val code: String) {
    SYNCED("SYNCED"), ADDED("ADDED"), DELETED("DELETED");

    companion object {
        fun getSyncState(code: String) = entries.find { it.code == code } ?: throw SQLiteException()
    }
}

fun SyncState.toSyncAction(): SyncStatus {
    return when (this) {
        SyncState.SYNCED -> SyncStatus.SYNCED
        SyncState.ADDED -> SyncStatus.ADDED
        SyncState.DELETED -> SyncStatus.DELETED
    }
}


fun SyncStatus.toSyncState(): SyncState {
    return when (this) {
        SyncStatus.SYNCED -> SyncState.SYNCED
        SyncStatus.ADDED -> SyncState.ADDED
        SyncStatus.DELETED -> SyncState.DELETED
    }
}