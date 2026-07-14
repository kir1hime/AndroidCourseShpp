package com.example.androidcourseshpp.data.source.network.utils

import com.example.androidcourseshpp.data.source.network.service.BackendException
import com.example.androidcourseshpp.data.source.network.service.ConnectionException
import com.example.androidcourseshpp.data.source.network.service.ResponseProcessingException
import com.example.androidcourseshpp.domain.utils.AppError
import com.example.androidcourseshpp.domain.utils.Result


suspend fun <T> wrapNetworkExceptions(toExecute: suspend () -> T): Result<T> {
    return try {

        val data = toExecute()
        Result.Success(data)

    } catch (_: BackendException) {
        Result.Error(AppError.BackendError)
    } catch (_: ResponseProcessingException) {
        Result.Error(AppError.ResponseProcessingError)
    } catch (_: ConnectionException) {
        Result.Error(AppError.ConnectionError)
    }

}