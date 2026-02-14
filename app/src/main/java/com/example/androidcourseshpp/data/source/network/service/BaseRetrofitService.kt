package com.example.androidcourseshpp.data.source.network.service

import com.example.androidcourseshpp.R
import com.example.androidcourseshpp.data.source.network.RetrofitConfig
import com.example.androidcourseshpp.data.source.network.dto.ErrorResponseDTO
import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.io.IOException

open class BaseRetrofitService(retrofitConfig: RetrofitConfig) {

    val retrofit = retrofitConfig.retrofit

    private val errorAdapter = retrofitConfig.gson.getAdapter(ErrorResponseDTO::class.java)

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

            BackendException(
                errorDTO?.message ?: R.string.backend_error.toString()
            )
        } catch (e: Exception) {
            throw ResponseProcessingException(e)
        }
    }
}

class ConnectionException(e: Exception) : Exception(e)
class BackendException(message: String) : Exception(message)
class ResponseProcessingException(e: Exception) : Exception(e)

