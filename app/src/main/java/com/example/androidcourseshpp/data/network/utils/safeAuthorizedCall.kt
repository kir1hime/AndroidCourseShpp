package com.example.androidcourseshpp.data.network.utils

import com.example.androidcourseshpp.data.local.userdata.UserDataProvider
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result


suspend fun <T> safeAuthorizedCall(
    userDataProvider: UserDataProvider,
    toExecute: suspend (userServerId: Long) -> Result<T, DataError.NetworkError>
): Result<T, DataError.NetworkError> {
    val userServerId = userDataProvider.getUserServerId()
        ?: return Result.Error(DataError.NetworkError.CURRENT_USER_NOT_FOUND)

    return toExecute(userServerId)
}