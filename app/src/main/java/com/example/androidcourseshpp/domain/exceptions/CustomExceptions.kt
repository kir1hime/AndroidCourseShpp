package com.example.androidcourseshpp.domain.exceptions

sealed class NetworkException(cause: Exception? = null, message: String = "") :
    Exception(message, cause) {
    class ConnectionException(e: Exception) : NetworkException(cause = e)
    class BackendException(message: String) : NetworkException(message = message)
    class ResponseProcessingException(e: Exception) : NetworkException(cause = e)
}

class LocalStorageException() : Exception()