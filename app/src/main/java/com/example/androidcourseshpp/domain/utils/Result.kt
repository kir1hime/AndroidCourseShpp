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
}