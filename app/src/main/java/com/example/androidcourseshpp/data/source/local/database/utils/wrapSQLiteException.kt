package com.example.androidcourseshpp.data.source.local.database.utils

import androidx.sqlite.SQLiteException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> wrapSQLiteException(toExecute: suspend () -> T): T {
    return try {
        withContext(Dispatchers.IO) {
            toExecute()
        }
    } catch (e: SQLiteException) {
        throw LocalStorageException().initCause(e)
    }
}

class LocalStorageException() : Exception()