package com.example.androidcourseshpp.data.source.network.utils

import android.util.Log
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import okio.IOException
import retrofit2.HttpException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    tag: String = "safeApiCall",
    apiCall: suspend () -> T
): Result<T, DataError.Network> = withContext(dispatcher) {

    try {
        Result.Success(apiCall())
    } catch (e: SerializationException ) {
        Log.e(tag, "Serialization failed: ${e.message}", e)
        Result.Error(DataError.Network.SERIALIZATION_ERROR)
    } catch (e: UnknownHostException) {
        Log.e(tag, "No internet connection: ${e.message}")
        Result.Error(DataError.Network.CONNECTION_ERROR)
    } catch (e: SocketTimeoutException) {
        Log.e(tag, "Request timed out: ${e.message}")
        Result.Error(DataError.Network.REQUEST_TIMEOUT_ERROR)
    } catch (e: IOException) {
        Log.e(tag, "Network error: ${e.message}")
        Result.Error(DataError.Network.CONNECTION_ERROR)
    } catch (e: HttpException) {
        handleHttpException(e, tag)
    } catch (e: Exception) {
        coroutineContext.ensureActive()
        Log.e(tag, "Unexpected error in safeApiCall", e)
        Result.Error(DataError.Network.UNKNOWN_ERROR)
    }
}


private fun handleHttpException(
    exception: HttpException,
    tag: String
): Result.Error<DataError.Network> {
    val errorBody = exception.response()?.errorBody()?.string()
    Log.e(tag, "HTTP ${exception.code()}: $errorBody")

    return when (exception.code()) {
        400 -> Result.Error(DataError.Network.INCORRECT_REQUEST_ERROR)
        401 -> Result.Error(DataError.Network.UNAUTHORIZED_ERROR)
        403 -> Result.Error(DataError.Network.ACCESS_DENIED_ERROR)
        404 -> Result.Error(DataError.Network.NOT_FOUNDED_ERROR)
        408 -> Result.Error(DataError.Network.REQUEST_TIMEOUT_ERROR)
        429 -> Result.Error(DataError.Network.TOO_MANY_REQUEST_ERROR)
        in 500..599 -> Result.Error(DataError.Network.SERVER_ERROR)
        else -> Result.Error(DataError.Network.UNKNOWN_ERROR)
    }
}