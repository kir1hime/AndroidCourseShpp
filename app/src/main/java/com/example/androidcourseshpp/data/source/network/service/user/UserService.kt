package com.example.androidcourseshpp.data.source.network.service.user

import com.example.androidcourseshpp.data.source.network.model.user.GetUserResponseModel
import com.example.androidcourseshpp.data.source.network.model.user.GetUsersResponseModel
import com.example.androidcourseshpp.data.source.network.model.user.UpdateUserRequestModel
import com.example.androidcourseshpp.data.source.network.model.user.UpdateUserResponseModel
import com.example.androidcourseshpp.domain.utils.DataError
import com.example.androidcourseshpp.domain.utils.Result

interface UserService {

    suspend fun updateUserInfo(updateUserRequestModel: UpdateUserRequestModel): Result<UpdateUserResponseModel, DataError.Network>
    suspend fun getUser(userId: Long): Result<GetUserResponseModel, DataError.Network>
    suspend fun getUsers(): Result<GetUsersResponseModel, DataError.Network>
}