package com.example.androidcourseshpp.data.network.service.refresh

import com.example.androidcourseshpp.data.network.RetrofitConfig
import com.example.androidcourseshpp.data.network.service.BaseRetrofitService
import com.example.androidcourseshpp.data.network.service.refresh.entity.RefreshTokenEntity
import com.example.androidcourseshpp.data.network.webapi.refreshAPI.RefreshAPI

class RefreshTokenServiceImpl(
    config: RetrofitConfig
) : BaseRetrofitService(config),
    RefreshTokenService {

    private val tokenRefreshApi = retrofit.create(RefreshAPI::class.java)

    override suspend fun refreshTokens(refreshTokenEntity: RefreshTokenEntity) {
        processRetrofitExceptions {
            tokenRefreshApi.refreshTokens(refreshTokenEntity.refreshToken)
        }
    }
}