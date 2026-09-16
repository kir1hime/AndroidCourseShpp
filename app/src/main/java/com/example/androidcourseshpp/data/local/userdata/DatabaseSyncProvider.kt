package com.example.androidcourseshpp.data.local.userdata

interface DatabaseSyncProvider {
    fun isDatabaseSynced(): Boolean
    fun markDatabaseAsSynced(isSynced: Boolean)
}