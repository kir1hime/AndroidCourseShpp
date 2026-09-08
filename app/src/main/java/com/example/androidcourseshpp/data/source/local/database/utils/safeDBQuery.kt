package com.example.androidcourseshpp.data.source.local.database.utils

import android.database.sqlite.SQLiteException
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result


suspend fun <T> safeDBQuery(toExecute: suspend () -> T): Result<T, DataError.LocalError> {
    return try {
        val data = toExecute()
        Result.Success(data)
    } catch (_: SQLiteException) {
        Result.Error(DataError.LocalError)
    }
}
