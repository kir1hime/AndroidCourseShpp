package com.example.androidcourseshpp.data.source.local.database.utils

import androidx.room.TypeConverter

class SyncStateConverter {

    @TypeConverter
    fun fromSyncState(state: SyncState): String {
        return state.name
    }

    @TypeConverter
    fun toSyncState(value: String): SyncState {
        return SyncState.valueOf(value)
    }
}