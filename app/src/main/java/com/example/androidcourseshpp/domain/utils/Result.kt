package com.example.androidcourseshpp.domain.utils


sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val error: AppError) : Result<Nothing>()

}

sealed class AppError {
    data object BackendError : AppError()
    data object ConnectionError : AppError()
    data object ResponseProcessingError : AppError()
    data object LocalStorageError : AppError()
    data object SyncContactsError : AppError()
}

suspend fun <T> Result<T>.onSuccess(toExecute: suspend (T) -> Unit): Result<T> {
    if (this is Result.Success) {
        toExecute(this.data)
    }
    return this
}

suspend fun <T> Result<T>.onError(toExecute: suspend () -> Unit): Result<T> {
    if (this is Result.Error) {
        toExecute()
    }
    return this
}