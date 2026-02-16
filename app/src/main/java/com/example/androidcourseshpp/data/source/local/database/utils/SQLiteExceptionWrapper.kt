package com.example.androidcourseshpp.data.source.local.database.utils

import androidx.sqlite.SQLiteException
import com.example.androidcourseshpp.domain.utils.AppError

import com.example.androidcourseshpp.domain.utils.Result


suspend fun <T> wrapSQLiteException(toExecute: suspend () -> T): Result<T> {
    return try {
        val data = toExecute()
        Result.Success(data)
    } catch (_: SQLiteException) {
        Result.Error(AppError.LocalStorageError)
    }
}
