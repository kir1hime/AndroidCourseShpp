package com.example.androidcourseshpp.data.source.local.userdata

interface DatabaseSyncProvider {
    fun isDatabaseSynced(): Boolean
    fun markDatabaseAsSynced(isSynced: Boolean)
}