package com.example.androidcourseshpp.data.network.service


import com.example.androidcourseshpp.data.network.dto.ErrorResponseDTO
import com.google.gson.Gson
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Singleton

@Singleton
open class BaseRetrofitService {
    private val errorAdapter = Gson().getAdapter(ErrorResponseDTO::class.java)

    suspend fun <T> processRetrofitExceptions(request: suspend () -> T): T {
        return try {
            request()

        } catch (e: JsonParseException) {
            throw ResponseProcessingException(e)
        } catch (e: HttpException) {
            throw createBackendException(e)
        } catch (e: IOException) {
            throw ConnectionException(e)
        }
    }

    private fun createBackendException(e: HttpException): Exception {
        return try {
            val errorJson = e.response()?.errorBody()?.string().orEmpty()
            val errorDTO = errorAdapter.fromJson(errorJson)

            BackendException(errorDTO?.message ?: "Backend error")
        } catch (e: Exception) {
            throw ResponseProcessingException(e)
        }
    }
}

class ConnectionException(cause: Exception) : Exception(cause)
class BackendException(message: String) : Exception(message)
class ResponseProcessingException(cause: Exception) : Exception(cause)