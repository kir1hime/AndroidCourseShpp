package com.example.androidcourseshpp.data.local.database.utils

import androidx.room.TypeConverter

class SyncStateConverter {

    @TypeConverter
    fun fromSyncState(state: SyncState): String {
        return state.code
    }

    @TypeConverter
    fun toSyncState(code: String): SyncState {
        return SyncState.getSyncState(code)
    }
}