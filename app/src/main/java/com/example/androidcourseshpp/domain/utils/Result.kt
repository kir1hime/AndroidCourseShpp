package com.example.androidcourseshpp.domain.utils


sealed interface RootError
sealed interface Result<out D, out E : RootError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : RootError>(val error: E) : Result<Nothing, E>
}

inline fun <D, E : RootError, R> Result<D, E>.mapResult(map: (D) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(map(data))
        is Result.Error -> Result.Error(error)
    }
}

inline fun <D, E : RootError> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> {
    return when (this) {
        is Result.Error -> this
        is Result.Success -> {
            action(data)
            this
        }
    }
}

inline fun <D, E : RootError> Result<D, E>.onError(action: (E) -> Unit): Result<D, E> {
    return when (this) {
        is Result.Success-> this
        is Result.Error -> {
            action(error)
            this
        }
    }
}

interface DataError : RootError {
    enum class Network : DataError {
        CONNECTION_ERROR,
        UNKNOWN_ERROR,
        INCORRECT_REQUEST_ERROR,
        UNAUTHORIZED_ERROR,
        ACCESS_DENIED_ERROR,
        NOT_FOUNDED_ERROR,
        REQUEST_TIMEOUT_ERROR,
        TOO_MANY_REQUEST_ERROR,
        SERVER_ERROR,
        SERIALIZATION_ERROR
    }
}