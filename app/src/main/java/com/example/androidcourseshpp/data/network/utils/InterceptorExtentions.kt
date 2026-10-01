package com.example.androidcourseshpp.data.network.utils

import okhttp3.Interceptor
import okhttp3.Response

fun Interceptor.Chain.addAuthHeaderForRequest(
    token: String?,
    headerName: String,
    headerValueType: String
): Response {
    val request = this.request()
    if (token.isNullOrBlank()) return proceed(request)
    val updatedRequest = request.newBuilder()
        .addHeader(headerName, "$headerValueType $token")
        .build()
    return proceed(updatedRequest)
}