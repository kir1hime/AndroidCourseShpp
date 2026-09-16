package com.example.androidcourseshpp.data.network.service.user

import com.example.androidcourseshpp.data.network.model.user.GetUserResponseModel
import com.example.androidcourseshpp.data.network.model.user.GetUsersResponseModel
import com.example.androidcourseshpp.data.network.model.user.UpdateUserRequestModel
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface UserService {

    suspend fun updateUserInfo(updateUserRequestModel: UpdateUserRequestModel): Result<Unit, DataError.NetworkError>
    suspend fun getUser(userId: Long): Result<GetUserResponseModel, DataError.NetworkError>
    suspend fun getUsers(): Result<GetUsersResponseModel, DataError.NetworkError>
}