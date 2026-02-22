package com.example.androidcourseshpp.data.source.local.database.utils

import com.example.androidcourseshpp.domain.entity.contact.SyncAction

enum class SyncState {
    SYNCED, ADDED,  DELETED;
}

fun SyncState.toSyncAction(): SyncAction {
    return when (this) {
        SyncState.SYNCED -> SyncAction.SYNCED
        SyncState.ADDED -> SyncAction.ADDED
        SyncState.DELETED -> SyncAction.SYNCED
    }
}

fun SyncAction.fromSyncAction(): SyncState {
    return when (this) {
        SyncAction.SYNCED -> SyncState.SYNCED
        SyncAction.ADDED -> SyncState.ADDED
        SyncAction.DELETED -> SyncState.SYNCED
    }
}
